package persistence

/**
 * Defines an interface for serialization and deserialization operations.
 */
interface Serializer {

    /**
     * Writes an object to a storage medium.
     *
     * @param obj The object to be written
     * @throws Exception If there's an issue while writing the object
     */
    @Throws(Exception::class)
    fun write(obj: Any?)

    /**
     * Reads an object from a storage medium.
     *
     * @return The read object
     * @throws Exception If there's an issue while reading the object
     */
    @Throws(Exception::class)
    fun read(): Any?
}
