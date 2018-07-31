SUMMARY = "Socketcand is a daemon that provides access to CAN interfaces via a net interface."
SECTION = "socketcan"
## LICENSE = "CLOSED"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"
PR = "r0"

DEPENDS = "libconfig"
RDEPENDS_${PN}-dev += "${PN}-staticdev"

SRCREV = "df7fb4ff8a4439d7737fe2df3540e1ab7465721a"
SRC_URI = "git://github.com/dschanoeh/socketcand.git;protocol=http"

S = "${WORKDIR}/git"

inherit autotools update-alternatives
inherit autotools-brokensep
