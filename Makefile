INSTALL_DIR="${HOME}/.apps/lib/jars"
BIN_DIR="${HOME}/.apps/bin"

model:
	rm -rf ${PWD}/reflect/model/net
	bazel run //compiler:compile -- --gen java2 --out ${PWD}/reflect/model ${PWD}/reflect/model/*.thrift

resources:
	mkdir -p ${PWD}/core/generated
	rm -rf ${PWD}/core/generated/net
	bazel run //compiler:compile -- --gen java2 --out ${PWD}/core/generated ${PWD}/core/res/definitions/*.thrift

test:
	bazel test :tests

.PHONY: model resources test
