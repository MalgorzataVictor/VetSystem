package persistence

import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.io.xml.DomDriver
import models.Owner
import models.Pet
import models.Vet
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.lang.Exception

/**
 * XML Serializer implementation for serialization and deserialization using XStream library.
 *
 * @property file The file to read from or write to
 * @constructor Creates an XMLSerializer with the specified file
 */
class XMLSerializer(private val file: File) : Serializer {
    /**
     * Reads an object from an XML file.
     *
     * @return The deserialized object
     * @throws Exception If there's an issue while reading from the file
     */
    @Throws(Exception::class)
    override fun read(): Any {
        val xStream = XStream(DomDriver())
        xStream.allowTypes(arrayOf(Pet::class.java, Vet::class.java, Owner::class.java))
        val inputStream = xStream.createObjectInputStream(FileReader(file))
        val obj = inputStream.readObject() as Any
        inputStream.close()
        return obj
    }

    /**
     * Writes an object to an XML file.
     *
     * @param obj The object to be serialized and written
     * @throws Exception If there's an issue while writing to the file
     */
    @Throws(Exception::class)
    override fun write(obj: Any?) {
        val xStream = XStream(DomDriver())
        val outputStream = xStream.createObjectOutputStream(FileWriter(file))
        outputStream.writeObject(obj)
        outputStream.close()
    }
}
