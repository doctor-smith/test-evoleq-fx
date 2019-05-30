package org.drx.evoleq.fx.test

import javafx.scene.Group
import javafx.stage.Stage
import javafx.stage.Window
import kotlinx.coroutines.delay
import org.drx.evoleq.evolving.Parallel
import org.drx.evoleq.fx.dsl.configure
import org.drx.evoleq.fx.dsl.fxButton
import org.drx.evoleq.fx.dsl.fxGroup
import org.drx.evoleq.fx.test.dsl.fxRunTest
import org.drx.evoleq.test.runTest
import org.junit.Test

class ApplicationTest {

    @Test fun showInTestStageTest() = fxRunTest{
        var shown = false
        val groupComponent = fxGroup<Boolean>{
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
        val stub = showInTestStage(groupComponent).get()
        assert(stub.stubs.size == 1)
        //delay(2_000)
        assert(shown)

    }

}