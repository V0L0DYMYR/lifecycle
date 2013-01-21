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

var TicketView = Backbone.View.extend({
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
    render: function(){
        var that = this;
        var el = $(this.el);
        el.empty();
        this.collection.each(function(model){
            el.append(new TicketView({model:model}).render().el);
        });
    }
});