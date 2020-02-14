SUMMARY = "Socketcand is a daemon that provides access to CAN interfaces via a net interface."
SECTION = "socketcan"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"
PR = "r0"

DEPENDS = "libconfig"
RDEPENDS_${PN}-dev += "${PN}-staticdev"

SRCREV = "${AUTOREV}"
## SRC_URI = "git://github.com/dschanoeh/socketcand.git;protocol=https"
SRC_URI = "git://github.com/linux-can/socketcand.git;protocol=https"

S = "${WORKDIR}/git"

inherit autotools update-alternatives
inherit autotools-brokensep
