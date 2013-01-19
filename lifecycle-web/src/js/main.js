var Ticket = Backbone.Model.extend({
            defaults: {
            id: null,
            title: 'default title'
    }

});
var ticket = new Ticket();

var TicketList = Backbone.Collection.extend({
    model: Ticket,
    url: "http://localhost:8000/ticket"
});
var ticketList = new TicketList();