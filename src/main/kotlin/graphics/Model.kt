package graphics

import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL15.*
import java.nio.FloatBuffer
import java.nio.IntBuffer
import kotlin.properties.Delegates

class Model(private val vertices:FloatArray,private val textureCoordinates: FloatArray,private val indices:IntArray) {
    private val drawCount:Int = indices.size
    private var vertexId by Delegates.notNull<Int>()
    private var textureId by Delegates.notNull<Int>()
    private var indicesId by Delegates.notNull<Int>()

    init {
        genVerticesBuffer()
        genTexturesBuffer()
        genIndicesBuffer()
        unbind()
    }
    fun render(){
        enable(true)
        bindVertices()
        bindTextures()
        draw()
        enable(false)
    }
    private fun genVerticesBuffer(){
        vertexId = glGenBuffers()
        glBindBuffer(GL_ARRAY_BUFFER,vertexId)
        glBufferData(GL_ARRAY_BUFFER,createBuffer(vertices), GL_STATIC_DRAW)
    }
    private fun genTexturesBuffer(){
        textureId = glGenBuffers()
        glBindBuffer(GL_ARRAY_BUFFER,textureId)
        glBufferData(GL_ARRAY_BUFFER,createBuffer(textureCoordinates), GL_STATIC_DRAW)
    }
    private fun genIndicesBuffer(){
        indicesId = glGenBuffers()
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,indicesId)
        glBufferData(GL_ELEMENT_ARRAY_BUFFER,createBuffer(indices), GL_STATIC_DRAW)
    }
    private fun unbind(){
        glBindBuffer(GL_ARRAY_BUFFER,0)
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0)
    }
    private fun bindVertices(){
        glBindBuffer(GL_ARRAY_BUFFER,vertexId)
        GL11.glVertexPointer(2, GL_FLOAT,0,0)
    }
    private fun bindTextures(){
        glBindBuffer(GL_ARRAY_BUFFER,textureId)
        GL11.glTexCoordPointer(2, GL_FLOAT,0,0)
    }
    private fun draw(){
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,indicesId)
        GL11.glDrawElements(GL_TRIANGLES,drawCount, GL_UNSIGNED_INT,0)
    }
    private fun enable(boolean: Boolean){
        if(boolean){
            glEnableClientState(GL_VERTEX_ARRAY)
            glEnableClientState(GL_TEXTURE_COORD_ARRAY)
            return
        }
        unbind()
        glDisableClientState(GL_VERTEX_ARRAY)
        glDisableClientState(GL_TEXTURE_COORD_ARRAY)
    }
    private fun createBuffer(data:FloatArray):FloatBuffer{
        val buffer = BufferUtils.createFloatBuffer(data.size)
        buffer.put(data)
        buffer.flip()
        return buffer
    }
    private fun createBuffer(data:IntArray):IntBuffer{
        val buffer = BufferUtils.createIntBuffer(data.size)
        buffer.put(data)
        buffer.flip()
        return buffer
    }
}