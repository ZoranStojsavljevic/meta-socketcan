All three recipes:

	cannelloni/cannelloni.bb
	can-utils/can-utils.bb
	socketcan/socketcand.bb

Are generic, and use SRCREV = "${AUTOREV}" , latest revisions from all three used SRC_URIs:

	SRC_URI = "git://github.com/mguentner/cannelloni.git;protocol=https"
	SRC_URI = "git://github.com/linux-can/can-utils.git;protocol=https"
	SRC_URI = "git://github.com/linux-can/socketcand.git;protocol=https"
