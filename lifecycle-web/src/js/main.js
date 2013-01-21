var Ticket = Backbone.Model.extend({
            defaults: {
            id: null,
            title: 'default title'
    }
});

var TicketList = Backbone.Collection.extend({
    model: Ticket,
    url: "http://localhost:8000/ticket"
});

var TicketRowView = Backbone.View.extend({
    template: '#ticket_tmpl',
    tagName:  "tr",
    initialize: function() {
          this.listenTo(this.model, 'change', this.render);
          this.listenTo(this.model, 'destroy', this.remove);
    },
    render: function(){
        var tmplMarkup = $('#ticket_tmpl').html();
        var tmplCompiled =_.template(tmplMarkup, {model:this.model.toJSON()});
        this.$el.html(tmplCompiled);
        return this;
    },
    events:{
        "click button.delete" : "clear"
    },
    clear:function(){
        this.model.destroy();
    }
});

var TicketListView = Backbone.View.extend({
    el:'#ticket_list',
    initialize: function(){
          _.bindAll(this, 'render');
           this.render();
    },
    render: function(){
        var that = this;
        var el = $(this.el);
        el.empty();
        this.collection.each(function(model){
            el.append(new TicketRowView({model:model}).render().el);
        });
    }
});

var TicketPageView = Backbone.View.extend({
    initialize:function(){

    },
    render:function(){

    }
});

var TicketRouter = Backbone.Router.extend({
    routes: {
        "newTicket":    "newTicket",
        "editTicket/:ticketId":"edit"
    },
    newTicket:function(){
        console.log("new ticket");
    },
    edit:function(){
        console.log("edit ticket");
    }
});