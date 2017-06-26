package grls3shard

import com.jeffrick.grails.plugin.sharding.SelectedTenantHolder
import com.jeffrick.grails.plugins.sharding.Shard
import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import groovy.sql.Sql
@Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
@Transactional(readOnly = true)
class BookController {
    def shardService
    def switchingDataSource
    def index(Integer max) {
      /*  shardService.change("shard01");
        new Book(author: "someone from shard01",title: "something from shard01").save();

        render Book.list();

        shardService.change("shard02");
        new Book(author: "someone from shard02",title: "something from shard02").save();

        render Book.list();*/
        params.max = Math.min(max ?: 10, 100)
        respond Book.list(params), model:[bookCount: Book.count()]
    }
    def tenantSwitch(){
        addData()
        shardService.changeByTenant(Customer.findByCustomerHostName('shpg'))
        List<Book> books = Book.list()
        println"books"+books.title
        def row = new Sql(switchingDataSource).rows("select * from book")
        println "the rows=====>>>>> $row"
        render "hello"
    }
    def addData(){
        Shard.saveAll(
                new Shard(shardName: 'shard01',dateCreated: new Date(),ratio:'0.1',shardCapacity:'1000',shardUsage: 1),
                new Shard(shardName: 'shard02',dateCreated: new Date(),ratio:'0.1',shardCapacity:'1000',shardUsage: 0)
        )
        Customer.saveAll(
                new Customer(customerHostName:'shpg',customerName:'shpg',shard:'shard01',deerwalkId: 1),
                new Customer(customerHostName:'medcost',customerName:'medcost',shard:'shard02',deerwalkId: 2)
        )
    }


}
