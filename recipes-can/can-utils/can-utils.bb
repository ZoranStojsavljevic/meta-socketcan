DESCRIPTION = "can-utils application"
SECTION = "socketcan"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"
PR = "r0"

DEPENDS = "libsocketcan"
RDEPENDS_${PN}-dev += "${PN}-staticdev"

SRCREV = "${AUTOREV}"
SRC_URI = "git://github.com/linux-can/can-utils.git;branch=master;protocol=https"

SRC_URI[md5sum] = "f8500620c1fe8fd3ce01a5bfd6a03adb"
SRC_URI[sha256sum] = "ed03395b3d69eccb1c59e68414874485ce43c0da76d9f3e0ce2fd1d1f06f736c"

S = "${WORKDIR}/git"

inherit autotools pkgconfig

# Busybox ip doesn't support can interface configuration, use the real thing
RDEPENDS_${PN} += "iproute2"
