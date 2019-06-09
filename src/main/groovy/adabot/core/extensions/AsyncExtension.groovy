package adabot.core.extensions

import groovy.transform.CompileStatic
import io.reactivex.Flowable
import org.reactivestreams.Publisher

@CompileStatic
class AsyncExtension {
    static <T> Iterable<T> blockingIterable(final Publisher<T> publisher) {
        Flowable.fromPublisher(publisher).blockingIterable()
    }
}
