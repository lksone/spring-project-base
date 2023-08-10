/*
package com.lks.netty.io;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

*/
/**
 * @author lks
 * @description inputStream源码解析
 * @e-mail 1056224715@qq.com
 * @date 2023/5/30 17:38
 *//*

public abstract class InputStream implements Closeable {

    */
/**
     * 当使用skip方法时，最大的buffer size大小
     *//*

    private static final int MAX_SKIP_BUFFER_SIZE = 2048;

    */
/**
     * 默认的buffer size
     *//*

    private static final int DEFAULT_BUFFER_SIZE = 8192;

    */
/**
     * JDK11中增加了一个nullInputStream，即空模式实现，以便可以直接调用而不用判空（可以看如下的补充说明）
     *
     * @return
     *//*

    public static InputStream nullInputStream() {
        return new InputStream() {
            private volatile boolean closed;

            private void ensureOpen() throws IOException {
                if (closed) {
                    throw new IOException("Stream closed");
                }
            }

            @Override
            public int available() throws IOException {
                ensureOpen();
                return 0;
            }

            @Override
            public int read() throws IOException {
                ensureOpen();
                return -1;
            }

            @Override
            public int read(byte[] b, int off, int len) throws IOException {
                Objects.checkFromIndexSize(off, len, b.length);
                if (len == 0) {
                    return 0;
                }
                ensureOpen();
                return -1;
            }

            @Override
            public byte[] readAllBytes() throws IOException {
                ensureOpen();
                return new byte[0];
            }

            @Override
            public int readNBytes(byte[] b, int off, int len)
                    throws IOException {
                Objects.checkFromIndexSize(off, len, b.length);
                ensureOpen();
                return 0;
            }

            @Override
            public byte[] readNBytes(int len) throws IOException {
                if (len < 0) {
                    throw new IllegalArgumentException("len < 0");
                }
                ensureOpen();
                return new byte[0];
            }

            @Override
            public long skip(long n) throws IOException {
                ensureOpen();
                return 0L;
            }

            @Override
            public long transferTo(OutputStream out) throws IOException {
                Objects.requireNonNull(out);
                ensureOpen();
                return 0L;
            }

            @Override
            public void close() throws IOException {
                closed = true;
            }
        };
    }

    */
/**
     * 读取下一个字节的数据，如果没有则返回-1
     *
     * @return
     * @throws IOException
     *//*

    public abstract int read() throws IOException;

    */
/**
     * 将读取到的数据放在 byte 数组中，该方法实际上调用read(byte b[], int off, int len)方法
     *
     * @param b
     * @return
     * @throws IOException
     *//*

    public int read(byte b[]) throws IOException {
        return read(b, 0, b.length);
    }

    */
/**
     * 从第 off 位置读取<b>最多(实际可能小于)</b> len 长度字节的数据放到 byte 数组中，流是以 -1 来判断是否读取结束的;
     * 此方法会一直阻止，直到输入数据可用、检测到stream结尾或引发异常为止。
     *
     * @param b
     * @param off
     * @param len
     * @return
     * @throws IOException
     *//*

    public int read(byte b[], int off, int len) throws IOException {
        // 检查边界
        Objects.checkFromIndexSize(off, len, b.length);
        if (len == 0) {
            return 0;
        }

        // 读取下一个字节
        int c = read();
        // 读到stream末尾，则返回读取的字节数量为-1
        if (c == -1) {
            return -1;
        }
        //将当前字节存入到offer位置
        b[off] = (byte) c;

        // i用来记录取了多少个字节
        int i = 1;
        try {
            // 循环读取
            for (; i < len; i++) {
                c = read();
                // 读到stream末尾，则break
                if (c == -1) {
                    break;
                }
                b[off + i] = (byte) c;
            }
        } catch (IOException ee) {
        }
        // 返回读取到的字节个数
        return i;
    }


    */
/**
     * 分配的最大数组大小。
     * 由于一些VM在数组中保留一些头字，所以尝试分配较大的阵列可能会导致OutOfMemoryError（请求的阵列大小超过VM限制）
     *//*

    private static final int MAX_BUFFER_SIZE = Integer.MAX_VALUE - 8;

    */
/**
     * JDK9新增：读取 InputStream 中的所有剩余字节，调用readNBytes(Integer.MAX_VALUE)方法
     *
     * @return
     * @throws IOException
     *//*

    public byte[] readAllBytes() throws IOException {
        return readNBytes(Integer.MAX_VALUE);
    }

    */
/**
     * JDK11更新：读取 InputStream 中的剩余字节的指定上限大小的字节内容；
     * 此方法会一直阻塞，直到读取了请求的字节数、检测到流结束或引发异常为止。此方法不会关闭输入流。
     *
     * @param len
     * @return
     * @throws IOException
     *//*

    public byte[] readNBytes(int len) throws IOException {
        // 边界检查
        if (len < 0) {
            throw new IllegalArgumentException("len < 0");
        }
        // 缓存每次读取到的内容放到bufs，最后组装成result
        List<byte[]> bufs = null;
        // 最后读取到的内容
        byte[] result = null;
        int total = 0;
        // 剩余字节长度
        int remaining = len;
        int n;
        do {
            byte[] buf = new byte[Math.min(remaining, DEFAULT_BUFFER_SIZE)];
            int nread = 0;

            // 读取到结束为止，读取大小n可能大于或小于缓冲区大小
            while ((n = read(buf, nread, Math.min(buf.length - nread, remaining))) > 0) {
                nread += n;
                remaining -= n;
            }

            if (nread > 0) {
                if (MAX_BUFFER_SIZE - total < nread) {
                    throw new OutOfMemoryError("Required array size too large");
                }
                total += nread;
                if (result == null) {
                    result = buf;
                } else {
                    if (bufs == null) {
                        bufs = new ArrayList<>();
                        bufs.add(result);
                    }
                    bufs.add(buf);
                }
            }
            // 如果读不到内容（返回-1）或者没有剩余的字节，则跳出循环
        } while (n >= 0 && remaining > 0);

        if (bufs == null) {
            if (result == null) {
                return new byte[0];
            }
            return result.length == total ?
                    result : Arrays.copyOf(result, total);
        }

        // 组装最后的result
        result = new byte[total];
        int offset = 0;
        remaining = total;
        for (byte[] b : bufs) {
            int count = Math.min(b.length, remaining);
            System.arraycopy(b, 0, result, offset, count);
            offset += count;
            remaining -= count;
        }

        return result;
    }

    */
/**
     * JDK9新增：从输入流读取请求的字节数并保存在byte数组中； 此方法会一直阻塞，直到读取了请求的字节数、检测到流结束或引发异常为止。此方法不会关闭输入流。
     *
     * @param b
     * @param off
     * @param len
     * @return
     * @throws IOException
     *//*

    public int readNBytes(byte[] b, int off, int len) throws IOException {
        Objects.checkFromIndexSize(off, len, b.length);

        int n = 0;
        while (n < len) {
            int count = read(b, off + n, len - n);
            if (count < 0) {
                break;
            }
            n += count;
        }
        return n;
    }

    // 跳过指定个数的字节不读取
    public long skip(long n) throws IOException {

        long remaining = n;
        int nr;

        if (n <= 0) {
            return 0;
        }

        int size = (int) Math.min(MAX_SKIP_BUFFER_SIZE, remaining);
        byte[] skipBuffer = new byte[size];
        while (remaining > 0) {
            nr = read(skipBuffer, 0, (int) Math.min(size, remaining));
            if (nr < 0) {
                break;
            }
            remaining -= nr;
        }

        return n - remaining;
    }

    // 返回可读的字节数量
    public int available() throws IOException {
        return 0;
    }

    */
/**
     * 读取完，关闭流，释放资源
     *
     * @throws IOException
     *//*

    @Override
    public void close() throws IOException {
    }

    // 标记读取位置，下次还可以从这里开始读取，使用前要看当前流是否支持，可以使用 markSupport() 方法判断
    public synchronized void mark(int readlimit) {
    }

    // 重置读取位置为上次 mark 标记的位置
    public synchronized void reset() throws IOException {
        throw new IOException("mark/reset not supported");
    }

    // 判断当前流是否支持标记流，和上面两个方法配套使用。默认是false，由子类方法重写
    public boolean markSupported() {
        return false;
    }

    */
/**
     * JDK9新增：读取 InputStream 中的全部字节并写入到指定的 OutputStream 中
     *
     * @param out 输出流
     * @return
     * @throws IOException
     *//*

    public long transferTo(OutputStream out) throws IOException {
        Objects.requireNonNull(out, "out");
        long transferred = 0;
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        int read;
        while ((read = this.read(buffer, 0, DEFAULT_BUFFER_SIZE)) >= 0) {
            out.write(buffer, 0, read);
            transferred += read;
        }
        return transferred;
    }
}
*/
