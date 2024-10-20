SUMMARY = "Socketcand is a daemon that provides access to CAN interfaces via a net interface."
SECTION = "socketcan"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"
PR = "r0"

DEPENDS += "ninja-native libconfig libsocketcan"
RDEPENDS_${PN}-dev += "${PN}-staticdev"

SRC_URI = "git://github.com/linux-can/socketcand.git;branch=master;protocol=https"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"

inherit meson pkgconfig

EXTRA_OEMESON += "-Dlibconfig=true --buildtype=release"
