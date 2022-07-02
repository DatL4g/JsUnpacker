# Kotlin Multiplatform Javascript Unpacker

This a rebuilt unpacker for JavaScript blocks that were packed with Dean Edwards JavaScript's Packer.

The project is based on [php-javascript-unpacker](https://github.com/rkaradas/php-javascript-unpacker).

## Usage

The purpose of this script is to unpack automated data, e.g. site scraping, where you need to get the unpacked version of a packed JavaScript to parse out link or something like that.

**The UnpackUnitTest class provides some small examples and shows some limitations on how to use it.**

```kotlin
val isPacked = JsUnpacker.detect("some string here")
val unpacked = JsUnpacker.unpackAndCombine("simple example")

println(isPacked)
println(unpacked)
```