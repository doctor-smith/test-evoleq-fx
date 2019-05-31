/**
 * Copyright (c) 2019 Dr. Florian Schmidt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.drx.evoleq.fx.test.dsl

import javafx.application.Application
import kotlinx.coroutines.*
import org.drx.evoleq.evolving.Evolving
import org.drx.evoleq.fx.application.BgAppManager
import org.drx.evoleq.test.TestData
import org.drx.evoleq.test.testRunner
import org.drx.evoleq.time.Change
import org.drx.evoleq.time.happen
import org.testfx.api.FxToolkit


suspend fun fxPerformTest(timeout: Long = 10_000,test: suspend CoroutineScope.()->Unit): Evolving<suspend CoroutineScope.() -> Unit> {
    val change = Change(test)
    val changing = change.happen()
    testRunner.send(TestData(timeout,change))
    return changing
}


fun fxRunTest(timeout: Long = 10_000, test: suspend CoroutineScope.()->Unit): Unit = runBlocking {
    var m: Application? = null

        FxToolkit.registerPrimaryStage()
        m = FxToolkit.setupApplication { BgAppManager() }
        var validate: suspend CoroutineScope.()->Unit = {}
        try {
            validate = fxPerformTest(timeout){test()}.get()
        }finally {
            FxToolkit.cleanupApplication(m!!)
            FxToolkit.cleanupStages()
        }
        validate()
}