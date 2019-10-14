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
package org.drx.evoleq.fx.test

import javafx.scene.Group
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import org.drx.evoleq.evolving.Immediate
import org.drx.evoleq.fx.dsl.*
import org.drx.evoleq.fx.test.dsl.fxRunTest
import org.drx.evoleq.fx.test.dsl.launchTestStage
import org.drx.evoleq.fx.test.dsl.showInTestStage
import org.drx.evoleq.fx.test.dsl.showTestStage
import org.junit.Test

class ApplicationTest {

    @Test fun showInTestStageTest() = fxRunTest{
        var shown = false
        fun groupComponent() = fxGroup<Boolean>{
            id<Group>()
            stub(org.drx.evoleq.dsl.stub {  })
            view{configure {  }}
            child(fxButton {
                noStub()
                view{configure{
                    text = "Hello"
                }}
            })
            fxRunTime{
                shown = true
            }
        }
        val stub = GlobalScope.showInTestStage(groupComponent()).get()
        println(stub.stubs.size)
        assert(stub.stubs.size == 2)
        //delay(2_000)
        assert(shown)

    }
    @Test fun testApp() = fxRunTest{//runBlocking{

        val stageComponent = fxStage<Boolean> {
            id<StageId>()
            view{configure{}}
            scene(fxScene {
                noStub()
                root(fxStackPane {
                    noStub()
                    view { configure{} }

                })

            })
            stub(org.drx.evoleq.dsl.stub {
                evolve { b ->
                    Immediate { !b }
                }
            })
        }

        val stub = showTestStage(stageComponent).get()
        assert(stub.evolve(false).get())
        assert(stageComponent.evolve(false ).get())
        delay(1_000)
    }

    @Test fun testApp2() = fxRunTest{//runBlocking{

        val stageComponent = fxStage<Boolean> {
            id<StageId>()
            view{configure{}}
            scene(fxScene {
                noStub()
                root(fxStackPane {
                    noStub()
                    view { configure{} }

                })

            })
            stub(org.drx.evoleq.dsl.stub {
                evolve { b ->
                    Immediate { !b }
                }
            })
        }

        val stub = launchTestStage(stageComponent).get()
        assert(stub.evolve(false).get())
        assert(stageComponent.evolve(false ).get())
        delay(1_000)
    }


}