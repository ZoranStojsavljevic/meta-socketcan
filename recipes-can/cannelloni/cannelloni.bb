SUMMARY = "SocketCAN over Ethernet tunnel using UDP to transfer CAN frames between two machines"
SECTION = "socketcan"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"
PR = "r0"

## DEPENDS = "${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'systemd', '', d)}"
RDEPENDS_${PN}-dev += "${PN}-staticdev"

## SRCREV = "${AUTOREV}"
## SRCREV = "2558902f0b2c2750ce99d05ac3cfe149c391482b"
SRCREV = "e3ac7393b566345d057c2d17a4d328007caaacac"

SRC_URI = "git://github.com/mguentner/cannelloni.git;protocol=https"

S = "${WORKDIR}/git"

inherit pkgconfig cmake
inherit systemd

EXTRA_OECMAKE += "-DCMAKE_BUILD_TYPE=Release"

INSANE_SKIP_${PN} = "ldflags"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"
SOLIBS = ".so"
FILES_SOLIBSDEV = ""

do_install_append () {
	install -d ${D}${libdir}
	install -m 0755 ${WORKDIR}/build/libcannelloni-common.so ${D}${libdir}
}
