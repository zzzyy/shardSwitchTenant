package grls3shard

class BootStrap {

    def init = { servletContext ->
        new Sindex(shard: "shard01").save();
        new Sindex(shard: "shard02").save();
    }
    def destroy = {
    }
}
