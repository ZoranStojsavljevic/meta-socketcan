### meta-socketcan layer

Recipe can-utils.bb will be taken from meta-openembedded layer,
and it will represent the native one:

* [recipes-extended/socketcan/can-utils_2023.03.bb](https://git.openembedded.org/meta-openembedded/tree/meta-oe/recipes-extended/socketcan/can-utils_2023.03.bb?h=scarthgap)

```
SUMMARY = "Linux CAN network development utilities"
LICENSE = "GPL-2.0-only & BSD-3-Clause"
LIC_FILES_CHKSUM = "file://include/linux/can.h;endline=44;md5=a9e1169c6c9a114a61329e99f86fdd31"

DEPENDS = "libsocketcan"

SRC_URI = "git://github.com/linux-can/${BPN}.git;protocol=https;branch=master"

SRCREV = "cfe41963f3425e9adb01a70cfaddedf5e5982720"
```

Another two recipes:

	cannelloni/cannelloni.bb
	socketcan/socketcand.bb

Are generic, and use SRCREV = "${AUTOREV}" , latest revisions from used SRC_URIs:

	SRC_URI = "https://github.com/mguentner/cannelloni.git;protocol=https"
	SRC_URI = "https://github.com/linux-can/socketcand.git;protocol=https"
