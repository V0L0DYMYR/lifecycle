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
    render: function(){
        var tmplMarkup = $(this.template).html();
        var tmplCompiled =_.template(tmplMarkup, {model:this.model.toJSON()});
        $(this.el).append(tmplCompiled);
        return this;
    }
});

var TicketListView = Backbone.View.extend({
    el:'#ticket_list',
    render: function(){
        var that = this;
        var el = $(this.el);
        el.empty();
        this.collection.each(function(model){
            el.append(new TicketView({model:model, el:that.el}).render());
        });
    }
});