package me.rain.mtforum.core.network

/**
 * InputStream.readNBytes(int) 是 API 33+ 的方法，Android 12 上调用会抛
 * NoSuchMethodError（发帖插图/回帖插图/附件上传闪退的原因）。
 * 这里手动实现"最多读 limit 字节"的等价逻辑。
 */
fun java.io.InputStream.readUpTo(limit: Int): ByteArray {
    val out = java.io.ByteArrayOutputStream()
    val chunk = ByteArray(64 * 1024)
    var total = 0
    while (total < limit) {
        val n = read(chunk, 0, minOf(chunk.size, limit - total))
        if (n < 0) break
        out.write(chunk, 0, n)
        total += n
    }
    return out.toByteArray()
}
