package graphics

import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11.*
import java.awt.image.BufferedImage
import java.io.File
import java.nio.ByteBuffer
import javax.imageio.ImageIO

data class Texture(val image: BufferedImage,private val height:Int,private val width:Int,private val pixels:ByteBuffer) {
    private var id:Int = glGenTextures()

    init {
        glBindTexture(GL_TEXTURE_2D,id)

        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST.toFloat())
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST.toFloat())

        glTexImage2D(GL_TEXTURE_2D, 0 , GL_RGBA,width,height, 0, GL_RGBA, GL_BYTE,pixels)
    }
    fun bind(){
        glBindTexture(GL_TEXTURE_2D,id)
    }

    companion object{
        fun get(fileName: String): Texture?{
            try {
                val image = ImageIO.read(File("src/main/resources/textures/$fileName"))
                val height = image.height
                val width = image.width
                val pixelsRaw:IntArray = image.getRGB(0,0,width,height,null,0,width)
                val pixels = BufferUtils.createByteBuffer(width*height*4) //rgba
                for (i in 0 until width){
                    for(j in 0 until height){
                        val pixel = pixelsRaw[i*width+j]
                        pixels.put( ((pixel shr 16) and 0xFF).toByte() )
                        pixels.put( ((pixel shr 8) and 0xFF).toByte() )
                        pixels.put( (pixel and 0xFF).toByte() )
                        pixels.put( ((pixel shr 24) and 0xFF).toByte() )
                    }
                }
                pixels.flip()
                return Texture(image, height, width, pixels)
            }catch (e:Exception){
                e.printStackTrace()
            }
            return null
        }
    }
}

