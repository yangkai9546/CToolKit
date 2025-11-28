# CToolKit - IntelliJ IDEA Plugin

A handy toolbox plugin for IntelliJ IDEA with the following features:

## Features

1. **JSON Formatter** - Format and prettify JSON text
2. **URL Encoder/Decoder** - Encode or decode URL strings
3. **Base64 Encoder/Decoder** - Encode or decode Base64 strings
4. **Encryption/Decryption** - Encrypt or decrypt text with a custom key
5. **Random String Generator** - Generate random strings of specified length

## Usage

1. Select text in the editor
2. Right-click to open the context menu
3. Choose "CToolKit" → "Open Toolbox" from the menu
4. Select the desired tool from the main toolbox dialog
5. Each tool has its own dialog with input/output fields and action buttons

## Tools Details

### JSON Formatter
- Format JSON text with proper indentation
- Compress JSON to a single line
- Copy formatted/compressed JSON to clipboard

### URL Encoder/Decoder
- Encode text for use in URLs
- Decode URL-encoded text
- Copy results to clipboard

### Base64 Encoder/Decoder
- Encode text to Base64 format
- Decode Base64 text
- Copy results to clipboard

### Encryption/Decryption
- Encrypt text with a custom key
- Decrypt text with the same key
- Generate random encryption keys
- Copy results to clipboard

### Random String Generator
- Generate random strings of specified length
- Copy generated strings to clipboard

## Installation

1. Download the plugin JAR file
2. In IntelliJ IDEA, go to `File` → `Settings` → `Plugins`
3. Click the gear icon and select `Install Plugin from Disk...`
4. Select the downloaded JAR file
5. Restart IntelliJ IDEA

## Building from Source

```bash
./gradlew buildPlugin
```

The built plugin will be located in `build/libs/`.

## Running Tests

```bash
./gradlew test
```