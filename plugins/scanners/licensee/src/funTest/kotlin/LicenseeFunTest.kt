/*
 * Copyright (C) 2017 The ORT Project Authors (see <https://github.com/oss-review-toolkit/ort/blob/main/NOTICE>)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 * License-Filename: LICENSE
 */

package org.ossreviewtoolkit.plugins.scanners.licensee

import org.ossreviewtoolkit.model.LicenseFinding
import org.ossreviewtoolkit.model.TextLocation
import org.ossreviewtoolkit.scanner.AbstractPathScannerWrapperFunTest

class LicenseeFunTest : AbstractPathScannerWrapperFunTest() {
    override val scanner = LicenseeFactory.create()

    override val expectedFileLicenses = listOf(
        LicenseFinding("Apache-2.0", TextLocation("LICENSE", TextLocation.UNKNOWN_LINE), 100.0f)
    )

    override val expectedDirectoryLicenses = listOf(
        LicenseFinding("Apache-2.0", TextLocation("COPYING", TextLocation.UNKNOWN_LINE), 99.37578f),
        LicenseFinding("Apache-2.0", TextLocation("LICENCE", TextLocation.UNKNOWN_LINE), 99.5f),
        LicenseFinding("Apache-2.0", TextLocation("LICENSE", TextLocation.UNKNOWN_LINE), 100.0f)
    )
}
