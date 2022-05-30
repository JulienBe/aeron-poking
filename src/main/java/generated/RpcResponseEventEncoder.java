/* Generated SBE (Simple Binary Encoding) message codec. */
package generated;

import org.agrona.MutableDirectBuffer;
import org.agrona.DirectBuffer;


/**
 * RPC Sample Response Event
 */
@SuppressWarnings("all")
public final class RpcResponseEventEncoder
{
    public static final int BLOCK_LENGTH = 0;
    public static final int TEMPLATE_ID = 12;
    public static final int SCHEMA_ID = 688;
    public static final int SCHEMA_VERSION = 1;
    public static final java.nio.ByteOrder BYTE_ORDER = java.nio.ByteOrder.LITTLE_ENDIAN;

    private final RpcResponseEventEncoder parentMessage = this;
    private MutableDirectBuffer buffer;
    private int initialOffset;
    private int offset;
    private int limit;

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

    public MutableDirectBuffer buffer()
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

    public RpcResponseEventEncoder wrap(final MutableDirectBuffer buffer, final int offset)
    {
        if (buffer != this.buffer)
        {
            this.buffer = buffer;
        }
        this.initialOffset = offset;
        this.offset = offset;
        limit(offset + BLOCK_LENGTH);

        return this;
    }

    public RpcResponseEventEncoder wrapAndApplyHeader(
        final MutableDirectBuffer buffer, final int offset, final MessageHeaderEncoder headerEncoder)
    {
        headerEncoder
            .wrap(buffer, offset)
            .blockLength(BLOCK_LENGTH)
            .templateId(TEMPLATE_ID)
            .schemaId(SCHEMA_ID)
            .version(SCHEMA_VERSION);

        return wrap(buffer, offset + MessageHeaderEncoder.ENCODED_LENGTH);
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

    public RpcResponseEventEncoder putResult(final DirectBuffer src, final int srcOffset, final int length)
    {
        if (length > 250)
        {
            throw new IllegalStateException("length > maxValue for type: " + length);
        }

        final int headerLength = 4;
        final int limit = parentMessage.limit();
        parentMessage.limit(limit + headerLength + length);
        buffer.putInt(limit, length, java.nio.ByteOrder.LITTLE_ENDIAN);
        buffer.putBytes(limit + headerLength, src, srcOffset, length);

        return this;
    }

    public RpcResponseEventEncoder putResult(final byte[] src, final int srcOffset, final int length)
    {
        if (length > 250)
        {
            throw new IllegalStateException("length > maxValue for type: " + length);
        }

        final int headerLength = 4;
        final int limit = parentMessage.limit();
        parentMessage.limit(limit + headerLength + length);
        buffer.putInt(limit, length, java.nio.ByteOrder.LITTLE_ENDIAN);
        buffer.putBytes(limit + headerLength, src, srcOffset, length);

        return this;
    }

    public RpcResponseEventEncoder result(final String value)
    {
        final byte[] bytes = (null == value || value.isEmpty()) ? org.agrona.collections.ArrayUtil.EMPTY_BYTE_ARRAY : value.getBytes(java.nio.charset.StandardCharsets.UTF_8);

        final int length = bytes.length;
        if (length > 250)
        {
            throw new IllegalStateException("length > maxValue for type: " + length);
        }

        final int headerLength = 4;
        final int limit = parentMessage.limit();
        parentMessage.limit(limit + headerLength + length);
        buffer.putInt(limit, length, java.nio.ByteOrder.LITTLE_ENDIAN);
        buffer.putBytes(limit + headerLength, bytes, 0, length);

        return this;
    }

    public static int correlationId()
    {
        return 2;
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

    public RpcResponseEventEncoder putCorrelation(final DirectBuffer src, final int srcOffset, final int length)
    {
        if (length > 250)
        {
            throw new IllegalStateException("length > maxValue for type: " + length);
        }

        final int headerLength = 4;
        final int limit = parentMessage.limit();
        parentMessage.limit(limit + headerLength + length);
        buffer.putInt(limit, length, java.nio.ByteOrder.LITTLE_ENDIAN);
        buffer.putBytes(limit + headerLength, src, srcOffset, length);

        return this;
    }

    public RpcResponseEventEncoder putCorrelation(final byte[] src, final int srcOffset, final int length)
    {
        if (length > 250)
        {
            throw new IllegalStateException("length > maxValue for type: " + length);
        }

        final int headerLength = 4;
        final int limit = parentMessage.limit();
        parentMessage.limit(limit + headerLength + length);
        buffer.putInt(limit, length, java.nio.ByteOrder.LITTLE_ENDIAN);
        buffer.putBytes(limit + headerLength, src, srcOffset, length);

        return this;
    }

    public RpcResponseEventEncoder correlation(final String value)
    {
        final byte[] bytes = (null == value || value.isEmpty()) ? org.agrona.collections.ArrayUtil.EMPTY_BYTE_ARRAY : value.getBytes(java.nio.charset.StandardCharsets.UTF_8);

        final int length = bytes.length;
        if (length > 250)
        {
            throw new IllegalStateException("length > maxValue for type: " + length);
        }

        final int headerLength = 4;
        final int limit = parentMessage.limit();
        parentMessage.limit(limit + headerLength + length);
        buffer.putInt(limit, length, java.nio.ByteOrder.LITTLE_ENDIAN);
        buffer.putBytes(limit + headerLength, bytes, 0, length);

        return this;
    }

    public String toString()
    {
        if (null == buffer)
        {
            return "";
        }

        return appendTo(new StringBuilder()).toString();
    }

    public StringBuilder appendTo(final StringBuilder builder)
    {
        if (null == buffer)
        {
            return builder;
        }

        final RpcResponseEventDecoder decoder = new RpcResponseEventDecoder();
        decoder.wrap(buffer, initialOffset, BLOCK_LENGTH, SCHEMA_VERSION);

        return decoder.appendTo(builder);
    }
}
