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
          this.listenTo(this.collection, 'change', this.render);
          this.listenTo(this.collection, 'reset', this.render);
          this.listenTo(this.collection, 'add', this.render);
          this.collection.fetch();
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
    el:'#ticketDialog',
    initialize:function(){
        this.model = new Ticket();
        this.listenTo(this.model, 'reset', this.render);
        this.render();
    },
    render:function(){
        var template =  _.template($('#ticket_page_tmpl').html());
        var context = {model:this.model.toJSON()};
        var html = template(context);
        $(this.el).html(html);
        return this;
    },
    events:{
        "click a#saveTicket" : "saveTicket",
        "change input#inputTicketTitle":  "contentChanged"
    },
    contentChanged:function(e){
        this.inputTicketTitle = this.$('#inputTicketTitle');
        var input = this.inputTicketTitle.val();
        this.model.set({title:input});
        console.log('input ' + input);
    },
    saveTicket:function(){
        this.collection.create(this.model);
        console.log("save ", this.model.get('title'));
    },
    populateModel:function(ticketId){
        this.model = this.collection.find(function(model){
                return Number(model.get('id')) === ticketId;
            });
    }
});

var TicketRouter = Backbone.Router.extend({
    initialize:function(){
        var tickets = new TicketList();
        this.allTicketsView = new TicketListView({collection:tickets});
        this.ticketPage = new TicketPageView({collection:tickets});
        this.views = [this.allTicketsView, this.ticketPage];
        this.allTickets();
    },
    routes: {
        "allTickets":   "allTickets",
        "addTicketDialog":    "newTicket",
        "addTicketDialog/:ticketId":"edit"
    },
    allTickets:function(){
        this.hideAll();
        $(this.allTicketsView.el).show();
    },
    newTicket:function(){
        this.hideAll();
        $(this.ticketPage.el).show();
    },
    edit:function(ticketId){
        this.hideAll();
        this.ticketPage.populateModel(ticketId);
        $(this.ticketPage.el).show();
        console.log("edit ticket");
    },
    hideAll:function(){
        _.each(this.views, function(view){
            $(view.el).hide();
        });
    }
});