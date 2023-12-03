package controllers

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.googleapis.json.GoogleJsonResponseException
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.client.util.store.FileDataStoreFactory
import com.google.api.services.gmail.Gmail
import com.google.api.services.gmail.GmailScopes.GMAIL_SEND
import com.google.api.services.gmail.model.Message
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.nio.file.Paths
import java.util.*
import javax.mail.MessagingException
import javax.mail.Session
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
import javax.mail.Message.RecipientType.TO;

//Todo could make it a class and instance in API layer
object GmailAPI {
    //ToDo Update with project name
    private const val APPLICATION_NAME = "GMAILTEST"

    //ToDo Ensure that client secrets/keys/credentials are NOT pushed to github
    private const val TOKENS_DIRECTORY_PATH = "tokens"
    private const val CREDENTIALS_FILE_PATH = "/credentials.json"

    //ToDo replace with email to use
    private const val TEST_EMAIL = "oisinivory@gmail.com"

    private var gmailService:Gmail

    /**
     * Configures the Gmail object which will be used to send emails in the class.
     */
    init{
        val httpTransport: NetHttpTransport = GoogleNetHttpTransport.newTrustedTransport()
        val gsonFactory: GsonFactory = GsonFactory.getDefaultInstance()
        gmailService = Gmail.Builder(httpTransport, gsonFactory, getCredentials(httpTransport,gsonFactory))
            .setApplicationName(APPLICATION_NAME)
            .build()
    }


    /**
     * Given a [httpTransport] and a [gsonFactory] load credentials generated in the tokens folder, or will make a http request to get them
     */
    @Throws(IOException::class)
    private fun getCredentials(httpTransport: NetHttpTransport, gsonFactory: GsonFactory): Credential {
        val clientSecrets = GoogleClientSecrets.load(
            gsonFactory,
            GmailAPI::class.java.getResourceAsStream(CREDENTIALS_FILE_PATH)?.let { InputStreamReader(it) }
        )
        val flow = GoogleAuthorizationCodeFlow.Builder(
            httpTransport, gsonFactory, clientSecrets, mutableSetOf(GMAIL_SEND)
        )
            .setDataStoreFactory(FileDataStoreFactory(Paths.get(TOKENS_DIRECTORY_PATH).toFile()))
            .setAccessType("offline")
            .build()
        val receiver = LocalServerReceiver.Builder().setPort(8888).build()
        return AuthorizationCodeInstalledApp(flow, receiver).authorize("user")
    }

    /**
     * Given a [recipientEmailAddress], creates and sends and email with a given [subject] and [body]
     */
    //ToDo Decide what it should return
    @Throws(MessagingException::class, IOException::class)
    fun sendEmail(
        recipientEmailAddress: String?,
        subject:String?,
        body:String?
    ): Boolean {
        //Create a new mail Session
        val props = Properties()
        val session = Session.getDefaultInstance(props, null)

        //Create and configure the email to be sent to
        //ToDo maybe add validation to email? (Regex)
        val email = MimeMessage(session)
        email.setFrom(InternetAddress(TEST_EMAIL))
        email.addRecipient(TO, InternetAddress(recipientEmailAddress))
        email.subject = subject
        email.setText(body)

        //Get the email object in bytes.
        val buffer = ByteArrayOutputStream()
        email.writeTo(buffer)
        val rawMessageBytes = buffer.toByteArray()
        //Encode email in order to send it
        val encodedEmail = Base64.getEncoder().encodeToString(rawMessageBytes)
        //Create new mesasge with the encoded bytes
        var msg = Message()
        msg.setRaw(encodedEmail)

        //ToDo Decide on what to do on error
        try {
            //Try to send the email using the gmailService
            msg = gmailService.users().messages().send("me", msg).execute()
            println("Message id: " + msg.id)
            println(msg.toPrettyString())
        } catch (e: GoogleJsonResponseException) {
            val error = e.details
            if (error.code == 403) {
                System.err.println("Unable to send message: " + e.details)
            } else {
                return false
            }
        }
        return true;
    }
}