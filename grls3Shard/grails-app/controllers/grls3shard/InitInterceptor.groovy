package grls3shard


class InitInterceptor {


    public InitInterceptor(){
        matchAll()
    }

    boolean before() {

        true
    }

    boolean after() { true }

    void afterView() {
        // no-op
    }
}
