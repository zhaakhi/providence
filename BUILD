load('release', 'release_jar')
load('release', 'java_pkg_deb')

release_jar('core', '//core')
release_jar('core-jackson', '//core-jackson')
release_jar('reflect', '//reflect')
release_jar('messageio', '//messageio')
release_jar('thrift', '//thrift')

java_pkg_deb(
    name = "providence-compiler-deb",
    jar = "//tools:providence-compiler_deploy.jar",
    package = "providence-compiler",
)

java_pkg_deb(
    name = "providence-converter-deb",
    jar = "//tools:providence-converter_deploy.jar",
    package = "providence-converter",
)

filegroup(
    name = 'release',
    srcs = [
        ### libraries ###
        ':core',
        ':core-jackson',
        ':messageio',
        ':thrift',
        ':reflect',
        ### packages ###
        ':providence-compiler-deb',
        ':providence-converter-deb',
    ]
)

test_suite(
    name = 'all_tests',
    tests = [
        '//core:tests',
        '//core-jackson:tests',
        '//messageio:tests',
        '//reflect:tests',
        '//testing:tests',
        '//tools:tests',
        '//thrift:tests',
    ],
)
