# Sigfox Data Decoder

[Sigfox](https://www.sigfox.com) is a low-energy wireless network mainly used in the IoT world.
A Sigfox message permits to transmit 12 bytes of data in a user-defined schema.

This project is a data serialization system.

Based on a use-provided schema, the decoder will transform a Sigfox payload into the according data structure.

## Basic usage
```
String format = "0-7:int:temperature";
SigfoxDecoder decoder = new SigfoxDecoder(format);
SigfoxData data = decoder.decode("c101013030320e0c03ed8000");
assert(data.getInt("temperature") == 12);
```

## Licence
Sigfox Data Decoder is licensed under the terms of the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)
