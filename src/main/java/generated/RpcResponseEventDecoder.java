/* Generated SBE (Simple Binary Encoding) message codec. */
package generated;

import org.agrona.MutableDirectBuffer;
import org.agrona.DirectBuffer;


/**
 * RPC Sample Response Event
 */
@SuppressWarnings("all")
public final class RpcResponseEventDecoder
{
    public static final int BLOCK_LENGTH = 0;
    public static final int TEMPLATE_ID = 12;
    public static final int SCHEMA_ID = 688;
    public static final int SCHEMA_VERSION = 1;
    public static final java.nio.ByteOrder BYTE_ORDER = java.nio.ByteOrder.LITTLE_ENDIAN;

    private final RpcResponseEventDecoder parentMessage = this;
    private DirectBuffer buffer;
    private int initialOffset;
    private int offset;
    private int limit;
    int actingBlockLength;
    int actingVersion;

    public int sbeBlockLength()
    {
        return BLOCK_LENGTH;
    }

    public int sbeTemplateId()
    {
        return TEMPLATE_ID;
    }

    public int sbeSchemaId()
    {
        return SCHEMA_ID;
    }

    public int sbeSchemaVersion()
    {
        return SCHEMA_VERSION;
    }

    public String sbeSemanticType()
    {
        return "";
    }

    public DirectBuffer buffer()
    {
        return buffer;
    }

    public int initialOffset()
    {
        return initialOffset;
    }

    public int offset()
    {
        return offset;
    }

    public RpcResponseEventDecoder wrap(
        final DirectBuffer buffer,
        final int offset,
        final int actingBlockLength,
        final int actingVersion)
    {
        if (buffer != this.buffer)
        {
            this.buffer = buffer;
        }
        this.initialOffset = offset;
        this.offset = offset;
        this.actingBlockLength = actingBlockLength;
        this.actingVersion = actingVersion;
        limit(offset + actingBlockLength);

        return this;
    }

    public RpcResponseEventDecoder wrapAndApplyHeader(
        final DirectBuffer buffer,
        final int offset,
        final MessageHeaderDecoder headerDecoder)
    {
        headerDecoder.wrap(buffer, offset);

        final int templateId = headerDecoder.templateId();
        if (TEMPLATE_ID != templateId)
        {
            throw new IllegalStateException("Invalid TEMPLATE_ID: " + templateId);
        }

        return wrap(
            buffer,
            offset + MessageHeaderDecoder.ENCODED_LENGTH,
            headerDecoder.blockLength(),
            headerDecoder.version());
    }

    public RpcResponseEventDecoder sbeRewind()
    {
        return wrap(buffer, initialOffset, actingBlockLength, actingVersion);
    }

    public int sbeDecodedLength()
    {
        final int currentLimit = limit();
        sbeSkip();
        final int decodedLength = encodedLength();
        limit(currentLimit);

        return decodedLength;
    }

    public int encodedLength()
    {
        return limit - offset;
    }

    public int limit()
    {
        return limit;
    }

    public void limit(final int limit)
    {
        this.limit = limit;
    }

    public static int resultId()
    {
        return 1;
    }

    public static int resultSinceVersion()
    {
        return 0;
    }

    public static String resultCharacterEncoding()
    {
        return java.nio.charset.StandardCharsets.UTF_8.name();
    }

    public static String resultMetaAttribute(final MetaAttribute metaAttribute)
    {
        if (MetaAttribute.PRESENCE == metaAttribute)
        {
            return "required";
        }

        return "";
    }

    public static int resultHeaderLength()
    {
        return 4;
    }

    public int resultLength()
    {
        final int limit = parentMessage.limit();
        return (int)(buffer.getInt(limit, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF_FFFFL);
    }

    public int skipResult()
    {
        final int headerLength = 4;
        final int limit = parentMessage.limit();
        final int dataLength = (int)(buffer.getInt(limit, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF_FFFFL);
        final int dataOffset = limit + headerLength;
        parentMessage.limit(dataOffset + dataLength);

        return dataLength;
    }

    public int getResult(final MutableDirectBuffer dst, final int dstOffset, final int length)
    {
        final int headerLength = 4;
        final int limit = parentMessage.limit();
        final int dataLength = (int)(buffer.getInt(limit, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF_FFFFL);
        final int bytesCopied = Math.min(length, dataLength);
        parentMessage.limit(limit + headerLength + dataLength);
        buffer.getBytes(limit + headerLength, dst, dstOffset, bytesCopied);

        return bytesCopied;
    }

    public int getResult(final byte[] dst, final int dstOffset, final int length)
    {
        final int headerLength = 4;
        final int limit = parentMessage.limit();
        final int dataLength = (int)(buffer.getInt(limit, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF_FFFFL);
        final int bytesCopied = Math.min(length, dataLength);
        parentMessage.limit(limit + headerLength + dataLength);
        buffer.getBytes(limit + headerLength, dst, dstOffset, bytesCopied);

        return bytesCopied;
    }

    public void wrapResult(final DirectBuffer wrapBuffer)
    {
        final int headerLength = 4;
        final int limit = parentMessage.limit();
        final int dataLength = (int)(buffer.getInt(limit, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF_FFFFL);
        parentMessage.limit(limit + headerLength + dataLength);
        wrapBuffer.wrap(buffer, limit + headerLength, dataLength);
    }

    public String result()
    {
        final int headerLength = 4;
        final int limit = parentMessage.limit();
        final int dataLength = (int)(buffer.getInt(limit, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF_FFFFL);
        parentMessage.limit(limit + headerLength + dataLength);

        if (0 == dataLength)
        {
            return "";
        }

        final byte[] tmp = new byte[dataLength];
        buffer.getBytes(limit + headerLength, tmp, 0, dataLength);

        return new String(tmp, java.nio.charset.StandardCharsets.UTF_8);
    }

    public static int correlationId()
    {
        return 2;
    }

    public static int correlationSinceVersion()
    {
        return 0;
    }

    public static String correlationCharacterEncoding()
    {
        return java.nio.charset.StandardCharsets.UTF_8.name();
    }

    public static String correlationMetaAttribute(final MetaAttribute metaAttribute)
    {
        if (MetaAttribute.PRESENCE == metaAttribute)
        {
            return "required";
        }

        return "";
    }

    public static int correlationHeaderLength()
    {
        return 4;
    }

    public int correlationLength()
    {
        final int limit = parentMessage.limit();
        return (int)(buffer.getInt(limit, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF_FFFFL);
    }

    public int skipCorrelation()
    {
        final int headerLength = 4;
        final int limit = parentMessage.limit();
        final int dataLength = (int)(buffer.getInt(limit, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF_FFFFL);
        final int dataOffset = limit + headerLength;
        parentMessage.limit(dataOffset + dataLength);

        return dataLength;
    }

    public int getCorrelation(final MutableDirectBuffer dst, final int dstOffset, final int length)
    {
        final int headerLength = 4;
        final int limit = parentMessage.limit();
        final int dataLength = (int)(buffer.getInt(limit, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF_FFFFL);
        final int bytesCopied = Math.min(length, dataLength);
        parentMessage.limit(limit + headerLength + dataLength);
        buffer.getBytes(limit + headerLength, dst, dstOffset, bytesCopied);

        return bytesCopied;
    }

    public int getCorrelation(final byte[] dst, final int dstOffset, final int length)
    {
        final int headerLength = 4;
        final int limit = parentMessage.limit();
        final int dataLength = (int)(buffer.getInt(limit, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF_FFFFL);
        final int bytesCopied = Math.min(length, dataLength);
        parentMessage.limit(limit + headerLength + dataLength);
        buffer.getBytes(limit + headerLength, dst, dstOffset, bytesCopied);

        return bytesCopied;
    }

    public void wrapCorrelation(final DirectBuffer wrapBuffer)
    {
        final int headerLength = 4;
        final int limit = parentMessage.limit();
        final int dataLength = (int)(buffer.getInt(limit, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF_FFFFL);
        parentMessage.limit(limit + headerLength + dataLength);
        wrapBuffer.wrap(buffer, limit + headerLength, dataLength);
    }

    public String correlation()
    {
        final int headerLength = 4;
        final int limit = parentMessage.limit();
        final int dataLength = (int)(buffer.getInt(limit, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF_FFFFL);
        parentMessage.limit(limit + headerLength + dataLength);

        if (0 == dataLength)
        {
            return "";
        }

        final byte[] tmp = new byte[dataLength];
        buffer.getBytes(limit + headerLength, tmp, 0, dataLength);

        return new String(tmp, java.nio.charset.StandardCharsets.UTF_8);
    }

    public String toString()
    {
        if (null == buffer)
        {
            return "";
        }

        final RpcResponseEventDecoder decoder = new RpcResponseEventDecoder();
        decoder.wrap(buffer, initialOffset, actingBlockLength, actingVersion);

        return decoder.appendTo(new StringBuilder()).toString();
    }

    public StringBuilder appendTo(final StringBuilder builder)
    {
        if (null == buffer)
        {
            return builder;
        }

        final int originalLimit = limit();
        limit(initialOffset + actingBlockLength);
        builder.append("[RpcResponseEvent](sbeTemplateId=");
        builder.append(TEMPLATE_ID);
        builder.append("|sbeSchemaId=");
        builder.append(SCHEMA_ID);
        builder.append("|sbeSchemaVersion=");
        if (parentMessage.actingVersion != SCHEMA_VERSION)
        {
            builder.append(parentMessage.actingVersion);
            builder.append('/');
        }
        builder.append(SCHEMA_VERSION);
        builder.append("|sbeBlockLength=");
        if (actingBlockLength != BLOCK_LENGTH)
        {
            builder.append(actingBlockLength);
            builder.append('/');
        }
        builder.append(BLOCK_LENGTH);
        builder.append("):");
        builder.append("result=");
        builder.append('\'').append(result()).append('\'');
        builder.append('|');
        builder.append("correlation=");
        builder.append('\'').append(correlation()).append('\'');

        limit(originalLimit);

        return builder;
    }
    
    public RpcResponseEventDecoder sbeSkip()
    {
        sbeRewind();
        skipResult();
        skipCorrelation();

        return this;
    }
}
