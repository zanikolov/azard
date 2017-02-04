'use strict';

angular.module('kalafcheFrontendApp')
    .directive('saleModal', function () {
        return {
            templateUrl: 'views/directives/sale-modal-directive.html',
            restrict: 'E',
            controller: 'InStockController',
            replace:true,
            link: function postLink(scope, element, attrs) {
                scope.title = attrs.title;
                console.log(">>>>>> SaleModal dialog!");
                
                scope.$watch(attrs.visibility, function(value){
                    if(value == true) {
                        console.log(">>>>>> SaleModal dialog SHOW!");
                        $(element).modal('show');
                    }
                    else {
                        console.log(">>>>>> SaleModal dialog HIDE!");
                        $(element).modal('hide');
                    }
                });

                $(element).on('shown.bs.modal', function(){
                    scope.$apply(function(){
                        scope.showSaleModal = true;
                        console.log(">>>>>> " + scope.showSaleModal);
                    });
                });

                $(element).on('hidden.bs.modal', function(){
                    scope.$apply(function(){
                        scope.showSaleModal = false;
                        scope.partnerCode = null;
                        scope.salePrice = null;
                        console.log(">>>>>> " + scope.showSaleModal);
                    });
                });
            }
        };
    });