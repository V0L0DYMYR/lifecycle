var Ticket = Backbone.Model.extend({
    defaults: {
        id: null,
        title: 'default title',
        priority:0
    }
});

var TicketList = Backbone.Collection.extend({
    model: Ticket,
    url: "http://localhost:8000/ticket",
    initialize:function(){
        this.fetch();
    },
    comparator:function(node){
        return -node.get('priority');
    }
});

var TicketRowView = Backbone.View.extend({
    template: '#ticket_tmpl',
    tagName: 'tr',
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
        Backbone.pubSub.on('my-event', this.render, this);
    },
    render: function(){
        var that = this;
        var el = $(this.el);
        el.empty();
        el.append($('#ticket_table_tmpl').html());
        var tbody = $('#ticket_list_body');
        this.collection.each(function(model){
            tbody.append(new TicketRowView({model:model}).render().el);
        });
    }
});

var TicketPageView = Backbone.View.extend({
    el:'#ticketDialog',
    initialize:function(){
        this.model = new Ticket();
        this.render();
    },
    render:function(){
        console.log('render TicketPageView');
        var template =  _.template($('#ticket_page_tmpl').html());
        var context = {model:this.model.toJSON()};
        var html = template(context);
        $(this.el).html(html);
        return this;
    },
    events:{
        "click a#saveTicket" : "saveTicket",
        "change input#inputTicketTitle":  "contentChanged",
        "change select#inputTicketPriority":  "contentChanged"
    },
    bindInputToModel:function(inputTagId, fieldName){
        var element = this.$(inputTagId);
        var input = element.val();
        this.model.set(fieldName, input);
    },
    contentChanged:function(e){
        this.bindInputToModel('#inputTicketTitle', 'title');
        this.bindInputToModel('#inputTicketPriority', 'priority');
    },
    saveTicket:function(){
        var renderAllTickets = function(){
            console.log('allTickets should be re-rendered');
            Backbone.pubSub.trigger('my-event');
        };
        this.collection.create(this.model, { silent: true, wait: true, success:renderAllTickets});
        console.log("save ", this.model);
    },
    populateModel:function(ticketId){
        console.log('populate model id=' + ticketId);
        this.model = this.collection.get(ticketId);
        this.render();
    },
    populateEmptyModel:function(){
        this.model = new Ticket();
        this.render();
    }
});


var FeatureRequestsPage = Backbone.View.extend({
   el:'#feature_request_page',
   initialize:function(){
        this.render();
   },
   render:function(){
       var el = $(this.el);
       el.empty();
       el.append($('#feature_request_tmpl').html());
       return this;
   }
});

var TicketRouter = Backbone.Router.extend({
    initialize:function(){
        //events
        Backbone.pubSub = _.extend({}, Backbone.Events);

        //collections
        var tickets = new TicketList();

        //views
        this.allTicketsView = new TicketListView({collection:tickets});
        this.ticketPage = new TicketPageView({collection:tickets});
        this.featureRequest = new FeatureRequestsPage();

        this.views = [this.allTicketsView, this.ticketPage, this.featureRequest];
        this.allTickets();
    },
    routes: {
        "allTickets":   "allTickets",
        "addTicketDialog":    "newTicket",
        "addTicketDialog/:ticketId":"edit",
        "featureRequest":"featureRequest"
    },
    featureRequest:function(){
        this.hideAll();
        $(this.featureRequest.el).show();
    },
    allTickets:function(){
        this.hideAll();
        $(this.allTicketsView.el).show();
        this.allTicketsView.render();
    },
    newTicket:function(){
        this.hideAll();
        this.ticketPage.populateEmptyModel();
        $(this.ticketPage.el).show();
    },
    edit:function(ticketId){
        this.hideAll();
        this.ticketPage.populateModel(ticketId);
        $(this.ticketPage.el).show();
    },
    hideAll:function(){
        _.each(this.views, function(view){
            $(view.el).hide();
        });
    }
});
