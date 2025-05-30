/*
 * Copyright (C) 2024 The ORT Project Authors (see <https://github.com/oss-review-toolkit/ort/blob/main/NOTICE>)
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

package org.ossreviewtoolkit.plugins.advisors.blackduck

import com.blackduck.integration.bdio.model.externalid.ExternalId
import com.blackduck.integration.blackduck.api.generated.response.ComponentsView
import com.blackduck.integration.blackduck.api.generated.view.OriginView
import com.blackduck.integration.blackduck.api.generated.view.VulnerabilityView

interface ComponentServiceClient {
    fun getOriginView(searchResult: ComponentsView): OriginView?

    fun getVulnerabilities(originView: OriginView): List<VulnerabilityView>

    fun searchKbComponentsByPurl(purl: String): List<ComponentsView>

    fun searchKbComponentsByExternalId(externalId: ExternalId): List<ComponentsView>
}
