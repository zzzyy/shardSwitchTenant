package grls3shard

import grails.gorm.MultiTenant

class Book implements MultiTenant<Book> {

    String title
    String author

    static constraints = {
    }

    static mapping = {
        datasource:["shard01","shard02"]
    }

    String toString(){
       return "Title ===>> ${this.title}, Author ====>>> ${this.author}"
    }
}
