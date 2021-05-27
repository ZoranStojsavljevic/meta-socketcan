SUMMARY = "Socketcand is a daemon that provides access to CAN interfaces via a net interface."
SECTION = "socketcan"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
PR = "r0"

DEPENDS = "libconfig"
RDEPENDS_${PN}-dev += "${PN}-staticdev"

SRCREV = "${AUTOREV}"
## SRC_URI = "git://github.com/dschanoeh/socketcand.git;protocol=https"
SRC_URI = "git://github.com/linux-can/socketcand.git;protocol=https"

S = "${WORKDIR}/git"

inherit autotools update-alternatives
inherit autotools-brokensep
