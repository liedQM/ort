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

package org.ossreviewtoolkit.utils.spdx

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class SpdxCompoundExpressionTest : WordSpec({
    "Creating a compound expression" should {
        "fail if the expression has less than two children" {
            shouldThrow<IllegalArgumentException> {
                SpdxCompoundExpression(SpdxOperator.AND, emptyList())
            }

            shouldThrow<IllegalArgumentException> {
                SpdxCompoundExpression(SpdxOperator.AND, listOf(SpdxLicenseIdExpression("license")))
            }
        }
    }

    "Simplifying a compound expression" should {
        "inline nested children of the same operator" {
            val expression = SpdxCompoundExpression(
                SpdxOperator.AND,
                listOf(
                    SpdxCompoundExpression(
                        SpdxOperator.AND,
                        listOf(
                            SpdxLicenseIdExpression("MIT"),
                            SpdxCompoundExpression(
                                SpdxOperator.AND,
                                listOf(
                                    SpdxLicenseIdExpression("MIT"),
                                    SpdxLicenseIdExpression("Apache-2.0")
                                )
                            )
                        )
                    ),
                    SpdxLicenseIdExpression("Apache-2.0")
                )
            )

            // Compare string representations to not rely on semantic equality.
            expression.simplify().sorted().toString() shouldBe "Apache-2.0 AND MIT"
        }

        "create a single expression for equal AND-operands" {
            val expression = SpdxCompoundExpression(
                SpdxOperator.AND,
                listOf(
                    SpdxLicenseIdExpression("MIT"),
                    SpdxLicenseIdExpression("MIT")
                )
            )

            // Compare string representations to not rely on semantic equality.
            expression.simplify().toString() shouldBe "MIT"
        }

        "create a single expression for equal OR-operands" {
            val expression = SpdxCompoundExpression(
                SpdxOperator.OR,
                listOf(
                    SpdxLicenseIdExpression("MIT"),
                    SpdxLicenseIdExpression("MIT")
                )
            )

            // Compare string representations to not rely on semantic equality.
            expression.simplify().toString() shouldBe "MIT"
        }

        "remove redundant choices with a nested expression at the beginning" {
            val expression = "(Apache-2.0 OR MIT) AND MIT AND Apache-2.0 AND Apache-2.0 AND MIT".toSpdx()

            // Compare string representations to not rely on semantic equality.
            expression.simplify().sorted().toString() shouldBe "Apache-2.0 AND MIT"
        }

        "remove redundant choices with a nested expression in the middle" {
            val expression = "Apache-2.0 AND (Apache-2.0 OR MIT) AND MIT AND MIT".toSpdx()

            // Compare string representations to not rely on semantic equality.
            expression.simplify().sorted().toString() shouldBe "Apache-2.0 AND MIT"
        }

        "remove redundant choices with a nested expression at the end" {
            val expression = "MIT AND Apache-2.0 AND Apache-2.0 AND MIT AND (Apache-2.0 OR MIT)".toSpdx()

            // Compare string representations to not rely on semantic equality.
            expression.simplify().sorted().toString() shouldBe "Apache-2.0 AND MIT"
        }
    }
})
