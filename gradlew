#!/bin/bash
#
# Copyright 2015 the original author or authors.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

if [ -z "${GRADLE_HOME}" ]; then
    if [ -n "${JAVA_HOME}" ]; then
        GRADLE_HOME="${JAVA_HOME}/../gradle"
    fi
fi

if [ -z "${GRADLE_HOME}" ]; then
    GRADLE_HOME="$(cd "$(dirname "$0")" && pwd)"
fi

if [ ! -d "${GRADLE_HOME}" ]; then
    echo "Could not find Gradle installation at ${GRADLE_HOME}"
    exit 1
fi

exec "${GRADLE_HOME}/bin/gradle" "$@"
