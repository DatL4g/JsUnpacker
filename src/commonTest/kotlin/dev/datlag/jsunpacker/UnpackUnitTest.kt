package dev.datlag.jsunpacker

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class UnpackUnitTest {

    @Test
    fun callIsNotPacked() {
        assertFalse(JsUnpacker.detect(notPackedCall), "JsUnpacker detected not packed call as packed")
    }

    @Test
    fun functionIsNotPacked() {
        assertFalse(JsUnpacker.detect(notPackedFunction), "JsUnpacker detected not packed function as packed")
    }

    @Test
    fun callIsPacked() {
        assertTrue(JsUnpacker.detect(packedCall), "JsUnpacker did not detect packed call as packed")
    }

    @Test
    fun functionIsPacked() {
        assertTrue(JsUnpacker.detect(packedFunction), "JsUnpacker did not detect packed function as packed")
    }

    @Test
    fun callUnpackedCorrectly() {
        val unpacked = JsUnpacker.unpackAndCombine(packedCall)
        assertTrue(callUnpackCheck(unpacked), "JsUnpacker did not unpack call correctly")
    }

    private fun callUnpackCheck(unpacked: String?): Boolean {
        return unpacked == unpackedCall || unpacked == unpackedCallAllowed
    }

    @Test
    fun functionUnpackedCorrectly() {
        val unpacked = JsUnpacker.unpackAndCombine(packedFunction)
        assertTrue(functionUnpackCheck(unpacked), "JsUnpacker did not unpack function correctly")
    }

    private fun functionUnpackCheck(unpacked: String?): Boolean {
        return unpacked == unpackedFunction || unpacked == unpackedFunctionAllowed
    }

    @Test
    fun unpackMultipleCorrectly() {
        val (unpackedCall, unpackedFunction) = JsUnpacker.unpack(packedCall, packedFunction)
        assertTrue(callUnpackCheck(unpackedCall) && functionUnpackCheck(unpackedFunction), "JsUnpacker did not unpack call and function together correctly")
    }

    companion object {
        private const val notPackedCall = "alert('This is not packed and a plain call');"
        private const val packedCall = "eval(function(p,a,c,k,e,r){e=String;if(!''.replace(/^/,String)){while(c--)r[c]=k[c]||c;k=[function(e){return r[e]}];e=function(){return'\\\\w+'};c=1};while(c--)if(k[c])p=p.replace(new RegExp('\\\\b'+e(c)+'\\\\b','g'),k[c]);return p}('0(\\'1 2 3 4 5 6 7\\');',8,8,'alert|This|is|packed|and|a|plain|call'.split('|'),0,{}))"
        private const val unpackedCall = "alert('This is packed and a plain call');"
        private const val unpackedCallAllowed = "alert(\\'This is packed and a plain call\\');"

        private const val notPackedFunction = "function funNotPackedTest() { alert('This is not packed and a function'); }"
        private const val packedFunction = "eval(function(p,a,c,k,e,r){e=String;if(!''.replace(/^/,String)){while(c--)r[c]=k[c]||c;k=[function(e){return r[e]}];e=function(){return'\\\\w+'};c=1};while(c--)if(k[c])p=p.replace(new RegExp('\\\\b'+e(c)+'\\\\b','g'),k[c]);return p}('0 1(){2(\\'3 4 5 6 7 0\\')}',8,8,'function|funPackedTest|alert|This|is|packed|and|a'.split('|'),0,{}))"
        private const val unpackedFunction = "function funPackedTest() { alert('This is packed and a function'); }"
        private const val unpackedFunctionAllowed = "function funPackedTest(){alert(\\'This is packed and a function\\')}"
    }
}