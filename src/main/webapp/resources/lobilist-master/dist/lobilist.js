/**
 * @name LobiList - Responsive jQuery todo list plugin
 * LobiList is todo list jquery plugin. Support multiple list with different styles, communication to backend, drag & drop of todos
 *
 * @author Zura Sekhniashvili <zurasekhniashvili@gmail.com>
 * @version 1.0.0
 * @licence MIT
 */
$(function () {

	var StorageLocal = function () {
		var STORAGE_KEY = 'lobilist';


		this.addList = function(lobilistId, listTitle){
			console.log("TODO addList");
		};

		this.removeList = function(lobilistId, listId){
			console.log("TODO removeList");
		};

		this.addTodo = function(lobilistId, listId, itemData){
			console.log("TODO addTodod");

			var storage = this.getLobilistStorage(listId) || {};
			storage.items = storage.items || [];
			storage.items.push();
		};

		this.removeTodo = function(lobilistId, list, itemId){
			console.log("TODO removeTodo");
		};

		this.saveItemPositions = function(lobilistId){
			console.log("TODO saveItemPositions");
		};

		this.saveListPositions = function(){
			console.log("TODO saveItemPositions");
		};



		this.setListProperty = function (lobilistId, listId, property, value) {
			var listStorage = this.getListStorage(lobilistId, listId);
			listStorage[property] = value;
			return this.setListStorage(lobilistId, listId, listStorage);

			// var lobilistStorage = this.getLobilistStorage() || {};
			// lobilistStorage.lists = lobilistStorage.lists || [];
			// var listStorage = lobilistStorage.lists[listId] = lobilistStorage.lists[listId] || {};
			// listStorage[property] = value;
			// this.setStorage(storage);
		};

		this.getListProperty = function (lobilistId, listId, property) {
			var storage = this.getLobilistStorage(lobilistId);
			if (storage && storage.lists && storage.lists[listId]) {
				return storage.lists[listId][property];
			}
		};

		this.getLobilistStorage = function (lobilistId) {
			var storage = this.getStorage() || {};
			return storage[lobilistId] || {lists: {}};
		};

		this.setLobilistStorage = function (lobilistId, lobilistStorage) {
			var storage = this.getStorage() || {};
			storage[lobilistId] = lobilistStorage;
			return this.setStorage(storage);
		};

		this.getListStorage = function (lobilistId, listId) {
			var storage = this.getLobilistStorage(lobilistId);
			return storage.lists[listId] || {};
		};

		this.setListStorage = function(lobilistId, listId, listStorage){
			var lobilistStorage = this.getLobilistStorage(lobilistId);
			lobilistStorage.lists = lobilistStorage.lists || [];
			lobilistStorage.lists[listId] = listStorage;
			return this.setLobilistStorage(lobilistId, lobilistStorage);
		};

		this.getStorage = function () {
			return JSON.parse(localStorage.getItem(STORAGE_KEY) || null);
		};

		this.setStorage = function (data) {
			localStorage.setItem(STORAGE_KEY, JSON.stringify(data || {}))
		};
	};


	/**
	 * List class
	 *
	 * @class
	 * @param {Object} $lobiList - jQuery element
	 * @param {Object} options - Options for <code>List</code> 'class'
	 * @constructor
	 */
	var List = function ($lobiList, options) {
//		console.log('List 불러오기 : '+ JSON.stringify($lobiList)); // 추가 코드
//		console.log('List 불러오기'); // 추가 코드
		this.$lobiList = $lobiList;
		this.$options = options;
		this.$globalOptions = $lobiList.$options;
		this.$items = {};
		this._init();
	};

	List.prototype = {
			$lobiList: null,
			$el: null,
			$elWrapper: null,
			$options: {},
			$items: {},
			$globalOptions: {},
			$ul: null,
			$header: null,
			$title: null,
			$form: null,
			$footer: null,
			$body: null,
			$hasGeneratedId: false,
			storageObject: null,

			eventsSuppressed: false,

			isStateful: function () {
//				console.log("isStateful List.prototype"); // 추가코드, isStateful 함수가 2개 있음!
//				console.log("변경 리스트 id: "+this.$el.attr('id')); // 변경하는 리스트 id값
//				console.log("변경전 스타일: "+this.$globalOptions.listStyles[this.$el.attr('id')]); //변경하기 이전 값
				return !!this.$el.attr('id') && this.$lobiList.storageObject;
			},

			/**
			 *
			 * @private
			 */
			_init: function () {
				console.log('_init 함수 동작'); // 추가 코드
				var me = this;
				me.suppressEvents();
				if (!me.$options.id) {
					me.$options.id = me.$lobiList.getNextListId();
					me.$hasGeneratedId = true;
				}
				me.$elWrapper = $('<div class="lobilist-wrapper"></div>');
				me.$el = $('<div id="' + me.$options.id + '" class="lobilist"></div>').appendTo(me.$elWrapper);

				if (!me.$hasGeneratedId) {
					me.$el.attr('data-db-id', me.$options.id);
				}
				if (me.isStateful()) {
					me.$options.title = me.getSavedProperty('title') === undefined ? me.$options.title : me.getSavedProperty('title');
					me.$options.defaultStyle = me.getSavedProperty('style') === undefined ? me.$options.defaultStyle : me.getSavedProperty('style');
				}

				if (me.$options.defaultStyle) {
					me.$el.addClass(me.$options.defaultStyle);
				}
			
				//console.log('me._createHeader();');
				me.$header = me._createHeader();
				me.$title = me._createTitle();
				me.$body = me._createBody();
				me.$ul = me._createList();
				if (me.$options.items) {
					me._createItems(me.$options.items);
				}
				me.$form = me._createForm();
				me.$body.append(me.$ul, me.$form);
				me.$footer = me._createFooter();
				if (me.$globalOptions.sortable) {
					me._enableSorting();
				}
				me.resumeEvents();
			},

			getSavedProperty: function (property) {
				var me = this;
				if (!me.isStateful()){
					console.error("getSavedProperty 에러?");

					console.error("object is not stateful");
					return false;
				}
				return me.$lobiList.storageObject.getListProperty(me.$lobiList.getId(), me.getId(), property);
			},

			saveProperty: function (property, value) {
				var me = this;
				if (!me.isStateful()){
					console.error("saveProperty 에러가 무슨 의미?");
					console.error("object is not stateful");
					return false;
				}
				return me.$lobiList.storageObject.setListProperty(me.$lobiList.getId(), me.getId(), property, value);
			},

			/**
			 * Add item. If <code>action.insert</code> url is provided request is sent to the server.
			 * Server response example: <code>{"success": Boolean}</code>.
			 * If <code>response.success</code> is true item is added.
			 * Otherwise <code>errorCallback</code> callback is called if it was provided.
			 *
			 * @method addItem
			 * @param {Object} item - The item <code>Object</code>
			 * @param {Function} errorCallback - The callback which is called when server returned response but
			 * <code>response.success=false</code>
			 * @returns {List}
			 */
			addItem: function (item, errorCallback) {
				console.log("addItem 기능 시작 ");
				var me = this;
				if (me._triggerEvent('beforeItemAdd', [me, item]) === false) {
					return me;
				}

				item = me._processItemData(item);
				if (me.$globalOptions.actions.insert) {

					console.log("addItem insert 기능 ajax 동작 : "+ JSON.stringify(item));
					$.ajax(me.$globalOptions.actions.insert, {
						data: item,
						method: 'POST'
					})

					//res is JSON object of format {"success": Boolean, "id": Number, "msg": String}
					.done(function (res) { //done되지 않아서 res가 존재하지 않음?
						console.log('addItem .done 되었을 경우 동작');
						console.log('res: '+JSON.stringify(res));
						console.log('res.success : '+res.success);

						if (res.success) { //res.success 조건 발생 안함
							console.log('res.success : '+res.success);
							item.id = res.id;
							console.log('item.id : '+item.id);
							me._addItemToList(item);
						} else {
							if (errorCallback && typeof errorCallback === 'function') {
								errorCallback(res)
							}
						}
					});
				} else {
					item.id = me.$lobiList.getNextId();
					me._addItemToList(item);
				}




				return me;
			},

			/**
			 * Update item. If <code>action.update</code> url is provided request is sent to the server.
			 * Server response example: <code>{"success": Boolean}</code>.
			 * If <code>response.success</code> is true item is updated.
			 * Otherwise <code>errorCallback</code> callback is called if it was provided.
			 *
			 * @method updateItem
			 * @param {Object} item  - The item <code>Object</code> to update
			 * @param {Function} errorCallback - The callback which is called when server returned response but
			 * <code>response.success=false</code>
			 * @returns {List}
			 */
			updateItem: function (item, errorCallback) {
				var me = this;
				if (me._triggerEvent('beforeItemUpdate', [me, item]) === false) {
					return me
				}
				console.log('update 기능 ajax 코드'); // 추가 코드
				if (me.$globalOptions.actions.update) {
					$.ajax(me.$globalOptions.actions.update, {
						data: item,
						method: 'POST'
					})
					//res is JSON object of format {"id": Number, "success": Boolean, "msg": String}
					.done(function (res) {
						if (res.success) {
							me._updateItemInList(item);
						} else {
							if (errorCallback && typeof errorCallback === 'function') {
								errorCallback(res)
							}
						}
					});
				} else {
					me._updateItemInList(item);
				}
				return me;
			},

			/**
			 * Delete item from the list. If <code>action.delete</code> url is provided request is sent to the server.
			 * Server response example: <code>{"success": Boolean}</code>
			 * If <code>response.success=true</code> item is deleted from the list and <code>afterItemDelete</code> event
			 * if triggered. Otherwise <code>errorCallback</code> callback is called if it was provided.
			 *
			 * @method deleteItem
			 * @param {Object} item - The item <code>Object</code> to delete
			 * @param {Function} errorCallback - The callback which is called when server returned response but
			 * <code>response.success=false</code>
			 * @returns {List}
			 */
			deleteItem: function (item, errorCallback) {
				var me = this;
				if (me._triggerEvent('beforeItemDelete', [me, item]) === false) {
					return me
				}
				if (me.$globalOptions.actions.delete) {
					console.log('delete 기능을 _sendAjax로 보내기'); // 추가 코드

					return me._sendAjax(me.$globalOptions.actions.delete, {
						data: item,
						method: 'POST'
					})
					//res is JSON object of format
					.done(function (res) {
						console.log('item : '+item);
						if (res.success) {
							me._removeItemFromList(item);
						} else {
							if (errorCallback && typeof errorCallback === 'function') {
								errorCallback(res)
							}
						}
						
						//아이템 삭제시 작업진행도도 반영

						
					});
				} else {
					me._removeItemFromList(item);
				}
				return me;
			},

			/**
			 * If item does not have id, it is considered as new and is added to the list.
			 * If it has id it is updated. If update and insert actions are provided corresponding request is sent to the server
			 *
			 * @method saveOrUpdateItem
			 * @param {Object} item  - The item <code>Object</code>
			 * @param {Function} errorCallback - The callback which is called when server returned response but
			 * <code>response.success=false</code>
			 * @returns {List}
			 */
			saveOrUpdateItem: function (item, errorCallback) {
				var me = this;
				if (item.id) {
					console.log('saveOrUpdateItem에서 updateItem 호출'); //추가 코드
					me.updateItem(item, errorCallback);

				} else {
					console.log('saveOrUpdateItem에서 addItem 호출'); //추가 코드
					me.addItem(item, errorCallback);
				}
				return me;
			},

			/**
			 * Start title editing
			 *
			 * @method startTitleEditing
			 * @returns {List}
			 */
			startTitleEditing: function () {

				console.log('startTitleEditing')    
				var me = this;
				var input = me._createInput();

				console.log('input : '+input)   //추가 코드
				me.$title.attr('data-old-title', me.$title.html());
				input.val(me.$title.html());
				input.insertAfter(me.$title);
				me.$title.addClass('hide');
				me.$header.addClass('title-editing');
				input[0].focus();
				input[0].select();
      


				return me;
			},

			/**
			 * Finish title editing
			 *
			 * @method finishTitleEditing
			 * @returns {List}
			 */
			finishTitleEditing: function () {
				var me = this;
				var $input = me.$header.find('input');
				var oldTitle = me.$title.attr('data-old-title');
				me.$title.html($input.val()).removeClass('hide').removeAttr('data-old-title');
				if (me.isStateful()) {
					me.saveProperty( 'title', $input.val());
				}
				$input.remove();
				me.$header.removeClass('title-editing');

				console.log(oldTitle, $input.val()); // 이름변경 코드
				console.log(oldTitle); // 이름변경 코드

				var listId = me.getId();
				console.log('listId : '+listId); // 이름변경 코드


				me._triggerEvent('titleChange', [me, oldTitle, $input.val()]);
				if(oldTitle ==''){
					console.log('리스트 추가 동작');
					$.ajax({
						url:"/Task/listAddAjax",
						type:"get",
						data:{
							title : $input.val()
						}
					})
				}else{
					console.log('리스트 수정 동작');
							//이름 변경 ajax 코드 추가
				$.ajax({
					url:"/Task/titleChangeAjax",
					type:"get",
					data:{
						listId : listId,
						title : $input.val()
					}
				})
				}
	
				return me;
			},

			/**
			 * Cancel title editing
			 *
			 * @method cancelTitleEditing
			 * @returns {List}
			 */
			cancelTitleEditing: function () {
				var me = this;
				var $input = me.$header.find('input');
				if ($input.length === 0) {
					return me;
				}
				me.$title.html(me.$title.attr('data-old-title')).removeClass('hide');
				$input.remove();
				me.$header.removeClass('title-editing');
				return me;
			},

			/**
			 * Remove list
			 *
			 * @method remove
			 * @returns {List} - Just removed <code>List</code> instance
			 */
			remove: function () {
				var me = this;
				me.$lobiList.$lists.splice(me.$el.index(), 1);
				me.$elWrapper.remove();

//				console.log('remove 기능이 지우는 값! : '+ me);
//				console.log('remove 기능이 지우는 값! : '+ $lobiList.attr("id"));

//				console.log('remove 기능이 지우는 값 : '+JSON.stringify());

//				$.ajax({
//				url:"/Task/",
//				type:"get",
//				data:{
//				listId : listId,
//				title : $input.val()
//				}
//				})

				return me;
			},

			/**
			 * Start editing of item
			 *
			 * @method editItem
			 * @param {String} id - The id of the item to start updating
			 * @returns {List}
			 */
			editItem: function (id) {
				var me = this;
				var $item = me.$lobiList.$el.find('li[data-id=' + id + ']');
				var $form = $item.closest('.lobilist').find('.lobilist-add-todo-form').removeClass('hide');
				$item.closest('.lobilist').find('.lobilist-footer').addClass('hide');

				var itemData = $item.data('lobiListItem');

				for (var i in itemData) {
					if ($form[0][i]) {
						$form[0][i].value = itemData[i];
					}
				}
				// $form[0].id.value = $item.attr('data-id');
				// $form[0].title.value = $item.find('.lobilist-item-title').html();
				// $form[0].description.value = $item.find('.lobilist-item-description').html() || '';
				// $form[0].dueDate.value = $item.find('.lobilist-item-duedate').html() || '';
				return me;
			},

			/**
			 * Get position among siblings
			 *
			 * @returns {int}
			 */
			getPosition: function () {
				var me = this;
				return me.$elWrapper.index();
			},

			/**
			 * Get id for list
			 *
			 * @returns {int}
			 */
			getId: function () {
				var me = this;

				return me.$options.id;
			},

			/**
			 * Set the id of the list
			 *
			 * @param id
			 * @returns {List}
			 */
			setId: function (id) {
				var me = this;
				me.$el.attr('id', id);
				me.$options.id = id;
				me.$el.attr('data-db-id', me.$options.id);
				return me;
			},

			getItemPositions: function(){
				var positions = {};
				var $items = this.$el.find('.lobilist-item');
				$items.each(function(ind, item){
					var $item = $(item);
					positions[$item.attr('data-id')] = $item.index() + 1;
				});
				return positions;
			},

			getTitle: function(){
				return this.$title.html();
			},

			getStyle: function(){
				var classList = this.$el[0].className.split(/\s+/);
				for (var i = 0; i < classList.length; i++) {
					if (this.$options.listStyles.indexOf(classList[i]) > -1) {
						return classList[i];
					}
				}
				return null;
			},

			/**
			 * Suppress events. None of the events will be triggered until you call <code>resumeEvents</code>
			 * @returns {List}
			 */
			suppressEvents: function () {
				this.eventsSuppressed = true;
				return this;
			},

			/**
			 * Resume all events.
			 * @returns {List}
			 */
			resumeEvents: function () {
				this.eventsSuppressed = false;
				return this;
			},

			_processItemData: function (item) {
				var me = this;
				item.listId = me.$options.id;
				return $.extend({}, me.$globalOptions.itemOptions, item);
			},

			_createHeader: function () {
				//console.log('_createHeader');
				var me = this;
				var $header = $('<div>', {
					'class': 'lobilist-header'
				});
				var $actions = $('<div>', {
					'class': 'lobilist-actions'
				}).appendTo($header);
				if (me.$options.controls && me.$options.controls.length > 0) {
			
					if (me.$options.controls.indexOf('styleChange') > -1) {

						$actions.append(me._createDropdownForStyleChange());
					}

					if (me.$options.controls.indexOf('edit') > -1) {
						$actions.append(me._createEditTitleButton());
						$actions.append(me._createFinishTitleEditing());
						$actions.append(me._createCancelTitleEditing());
					}
					if (me.$options.controls.indexOf('add') > -1) {
						$actions.append(me._createAddNewButton());
					}
					if (me.$options.controls.indexOf('remove') > -1) {
						$actions.append(me._createCloseButton());
					}
				}
				me.$el.append($header);
				return $header;
			},

			_createTitle: function () {
				var me = this;
				var $title = $('<div>', {
					'class': 'lobilist-title',
					html: me.$options.title
				}).appendTo(me.$header);
				if (me.$options.controls && me.$options.controls.indexOf('edit') > -1) {
					$title.on('dblclick', function () {
						
						console.log('_createTitle >');
						me.startTitleEditing();
					});
				}

				return $title;
			},

			_createBody: function () {
				var me = this;
				return $('<div>', {
					'class': 'lobilist-body'
				}).appendTo(me.$el);

			},

			_createForm: function () {
//				console.log('_createForm 동작'); // 추가 코드

				var me = this;
				var $form = $('<form>', {
					'class': 'lobilist-add-todo-form hide'
				});
				$('<input type="hidden" name="id">').appendTo($form);
				$('<div class="form-group">').append(
						$('<input>', {
							'type': 'text',
							name: 'title',
							'class': 'form-control',
							placeholder: 'TODO title'
						})
				).appendTo($form);
				$('<div class="form-group">').append(
						$('<textarea>', {
							rows: '2',
							name: 'description',
							'class': 'form-control',
							'placeholder': 'TODO description'
						})
				).appendTo($form);
				$('<div class="form-group">').append(
						$('<input>', {
							'type': 'text',
							name: 'dueDate',
							'class': 'form-control',
							placeholder: 'Due Date'
						})
				).appendTo($form);
				var $ft = $('<div class="lobilist-form-footer">');
				$('<button>', {
					'class': 'btn btn-primary btn-sm btn-add-todo',
					html: 'Add'
				}).appendTo($ft);
				$('<button>', {
					type: 'button',
					'class': 'btn btn-default btn-sm btn-discard-todo',
					html: '<i class="glyphicon glyphicon-remove-circle"></i>'
				}).click(function () {
					$form.addClass('hide');
					me.$footer.removeClass('hide');
				}).appendTo($ft);
				$ft.appendTo($form);

				me._formHandler($form);

				me.$el.append($form);
				return $form;
			},

			_formHandler: function ($form) {
//				console.log('_formHandler 동작'); // 추가 코드
				var me = this;
				$form.on('submit', function (ev) {
					ev.preventDefault();
					me._submitForm();
				});
			},

			_submitForm: function () {
//				console.log('_submitForm 동작'); // 추가 코드
				//due date 정규식
				var me = this;

				var dayRegExp = /^(19|20)\d{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[0-1])$/;
				var due = me.$form[0].dueDate.value
				var testdue = dayRegExp.test(due);
				console.log(testdue)	



				if (!me.$form[0].title.value) {
					me._showFormError('title', 'Title can not be empty');

				}
				if(due != ""){
					if (!testdue){
						me._showFormError('dueDate', '형식에 맞게 넣어주세요'); // 정규식 에러 알림
						return
					}
				}

				var formData = {},
				$inputs = me.$form.find('[name]');
				$inputs.each(function (ind, el) {
					formData[el.name] = el.value;
				});
				me.saveOrUpdateItem(formData);

				me.$form.addClass('hide');
				me.$footer.removeClass('hide');
			},


			_createFooter: function () {
				var me = this;
				var $footer = $('<div>', {
					'class': 'lobilist-footer'
				});
				$('<button>', {
					type: 'button',
					'class': 'btn-link btn-show-form',
					'html': 'Add new'
				}).click(function () {
					me._resetForm();
					me.$form.removeClass('hide');
					$footer.addClass('hide');
				}).appendTo($footer);
				me.$el.append($footer);
				return $footer;
			},

			_createList: function () {
				var me = this;
				var $list = $('<ul>', {
					'class': 'lobilist-items'
				});
				me.$el.append($list);
				return $list;
			},

			_createItems: function (items) {
				var me = this;
				for (var i = 0; i < items.length; i++) {
					me._addItem(items[i]);
				}
			},

			/**
			 * This method is called when plugin is initialized
			 * and initial items are added to the list
			 *
			 * @type Object
			 */
			_addItem: function (item) {
				var me = this;
				if (!item.id) {
					item.id = me.$lobiList.getNextId();
				}
				if (me._triggerEvent('beforeItemAdd', [me, item]) !== false) {
					item = me._processItemData(item);
					me._addItemToList(item);
				}
			},

			_createCheckbox: function () {
				var me = this;
				var $item = $('<input>', {
					'type': 'checkbox'
				});

				$item.change(function () {
					console.log("_createCheckbox");
					
					me._onCheckboxChange(this);
				});
				return $('<label>', {
					'class': 'checkbox-inline lobilist-check'
				}).append($item);
			},

			_onCheckboxChange: function (checkbox) {
				var me = this;
				var $this = $(checkbox),
				$todo = $this.closest('.lobilist-item'),
				item = $todo.data('lobiListItem');
				item.done = $this.prop('checked');
				

				console.log("item.id: "+item.id); // 대상 아이템 아이디
				console.log("item.done: "+item.done); // 체크유무
				
				//ajax 코드 추가 checkboxChangeAjax
				$.ajax({
					url:"/Task/checkboxChangeAjax",
					type:"get",
					data:{
						id : item.id,
						done : item.done
					}
				})
				
				
				//ajax 코드 추가 작업진행바
				$.ajax({
				type : 'get',
				url : '/Task/selectCount',
				datatype : 'json',
				success : function(data) {
					console.log(data.to);
					$('#selector').css('width', data.to + '%');

				},
				error : function(error) {
					alert('data error');
				}
			});
				
				
				if (item.done) {
					me._triggerEvent('afterMarkAsDone', [me, item])
				} else {
					me._triggerEvent('afterMarkAsUndone', [me, item])
				}

				$this.closest('.lobilist-item').toggleClass('item-done');
			},

			_createDropdownForStyleChange: function () {
				var me = this;

				var buttonOptions = {
						'type': 'button',
						'data-toggle': 'dropdown',
						'class': 'btn btn-default btn-xs',
						'html': '<i class="glyphicon glyphicon-th"></i>'
				};
				var dropdownOptions = {
						'class': 'dropdown'
				};
				var menuOptions = {
						'class': 'dropdown-menu dropdown-menu-right'
				};
				if (me.$options.forAngularJs){
					buttonOptions['uib-dropdown-toggle'] = '';
					dropdownOptions['uib-dropdown'] = '';
					menuOptions['uib-dropdown-menu'] = '';
				}

				var $dropdown = $('<div>', dropdownOptions).append(
						$('<button>', buttonOptions)
				);
				var $menu = $('<div>', menuOptions).appendTo($dropdown);

				for (var i = 0; i < me.$globalOptions.listStyles.length; i++) {
					var st = me.$globalOptions.listStyles[i];
//					console.log(i+' 번 스타일 st: '+st); // 스타일 출력
					$('<div class="' + st + '"></div>')
					.on('mousedown', function (ev) {
						ev.stopPropagation()
					})
					.click(function () {
						var classes = me.$el[0].className.split(' ');
						var oldClass = null;
						for (var i = 0; i < classes.length; i++) {
							if (me.$globalOptions.listStyles.indexOf(classes[i]) > -1) {
								oldClass = classes[i];
							}
						}
						me.$el.removeClass(me.$globalOptions.listStyles.join(" "))
						.addClass(this.className);

						me.saveProperty('style', this.className);
						console.log("styleChange 동작");
						console.log(oldClass +" 에서");
						console.log(this.className +" 로 변경");
						console.log(me); // 아이디

						var obj1 = me.$el.find('.lobilist-wrapper lobilist');
						console.log(obj1);
						var obj2 = obj1[Object.keys(obj1)[1]];
						console.log(obj2);
						var obj3 = obj2[Object.keys(obj2)[0]];
						console.log(obj3);
						console.log(obj3.id);
						
						
						
						me._triggerEvent('styleChange', [me, oldClass, this.className]);

						// ajax 코드 추가 styleChange
						$.ajax({
							url:"/Task/styleChangeAjax",
							type:"get",
							data:{
								id : obj3.id,
								defaultStyle : this.className
							}
						})
						
						
						
						
					})
					.appendTo($menu);
				}
				return $dropdown;
			},

			_createEditTitleButton: function () {
				var me = this;
				var $btn = $('<button>', {
					'class': 'btn btn-default btn-xs',
					html: '<i class="glyphicon glyphicon-edit"></i>'
				});
				$btn.click(function () {
					console.log('_createEditTitleButton >');
					me.startTitleEditing();
				});

				return $btn;
			},

			_createAddNewButton: function () {
				//console.log('_createAddNewButton');
				var me = this;
				var $btn = $('<button>', {
					'class': 'btn btn-default btn-xs',
					html: '<i class="glyphicon glyphicon-plus"></i>'
				});
				
				$btn.click(function () {
					
					console.log('리스트 추가 버튼 클릭 2');
					var list = me.$lobiList.addList();
					
					list.startTitleEditing();
					
				});
				
				// 다른 버튼도 추가
	
//				$('#create_btn').click(function() {
//					console.log('create_btn 버튼 클릭(js)');
//				var list = me.$lobiList.addList();
//				list.startTitleEditing();
//		    	});
				
				
				
				//console.log('리스트 추가 리턴? ');

				return $btn;
			},

			_createCloseButton: function () {
				var me = this;
				var $btn = $('<button>', {
					'class': 'btn btn-default btn-xs',
					html: '<i class="glyphicon glyphicon-remove"></i>'
				});
				$btn.click(function () {
					me._onRemoveListClick();
				});
				return $btn;
			},

			_onRemoveListClick: function () {


				if (confirm("정말 삭제하시겠습니까??") == true){    //확인

					var me = this;

					me._triggerEvent('beforeListRemove', [me]);




					console.log('_onRemoveListClick 리스트 지우기');

					console.log('me');
					console.log(me); // 존재하는 모든 리스트 출력						

					var obj1 = me.$el.find('.lobilist-wrapper lobilist');
					console.log(obj1);


					var obj2 = obj1[Object.keys(obj1)[1]];
					console.log(obj2);

					var obj3 = obj2[Object.keys(obj2)[0]];
					console.log(obj3);
					console.log(obj3.id);



					//ajax 리스트 삭제
					$.ajax({
						url:"/Task/RemoveList_Ajax",
						type:"get",
						data:{
							id : obj3.id
						}
					})




					me.remove();
					

console.log("지우고 나서");
					
					me._triggerEvent('afterListRemove', [me]);






					return me;

				}else{   return false;

				}
			},

			_createFinishTitleEditing: function () {
				var me = this;
				var $btn = $('<button>', {
					'class': 'btn btn-default btn-xs btn-finish-title-editing',
					html: '<i class="glyphicon glyphicon-ok-circle"></i>'
				});
				$btn.click(function () {
					me.finishTitleEditing();

					console.log('me.finishTitleEditing 끝');
				});
				return $btn;
			},

			_createCancelTitleEditing: function () {
				var me = this;
				var $btn = $('<button>', {
					'class': 'btn btn-default btn-xs btn-cancel-title-editing',
					html: '<i class="glyphicon glyphicon-remove-circle"></i>'
				});
				$btn.click(function () {
					me.cancelTitleEditing();
				});
				return $btn;
			},

			//리스트 이름 생성
			_createInput: function () {
				console.log("_createInput");
				var me = this;
				var input = $('<input type="text" class="form-control">');  
				input.on('keyup', function (ev) {
					if (ev.which === 13) {
						
						
						if (input[0].value == '') { // 리스트 네임
							alert('리스트 이름을 입력해 주세요!');
							return
						}
						
						me.finishTitleEditing();
				
					}
				});
				return input;
			},

			_showFormError: function (field, error) {
				var $fGroup = this.$form.find('[name="' + field + '"]').closest('.form-group')
				.addClass('has-error');
				$fGroup.find('.help-block').remove();
				$fGroup.append(
						$('<span class="help-block">' + error + '</span>')
				);
			},

			_resetForm: function () {
				var me = this;
				me.$form[0].reset();
				me.$form[0].id.value = "";
				me.$form.find('.form-group').removeClass('has-error').find('.help-block').remove();
			},

			_enableSorting: function () {
				var me = this;
				me.$el.find('.lobilist-items').sortable({
					connectWith: '.lobilist .lobilist-items',
					items: '.lobilist-item',
					handle: '.drag-handler',
					cursor: 'move',
					placeholder: 'lobilist-item-placeholder',
					forcePlaceholderSize: true,
					opacity: 0.9,
					revert: 70,
					start: function (event, ui) {
						console.log("_enableSorting start "); //

						var $todo = ui.item,
						$list = $todo.closest('.lobilist');
						$todo.data('oldIndex', $todo.index());
						$todo.data('oldList', $list.data('lobiList'));
					},
					update: function (event, ui) {
						console.log("_enableSorting update 아이템 이동"); //
						var $todo = ui.item,
						item = $todo.data('lobiListItem'),
						oldList = $todo.data('oldList'),
						oldIndex = $todo.data('oldIndex'),
						currentIndex = $todo.index(),
						$itemWrapper = me.$el.find('.lobilist-items');
						$list = $todo.closest('.lobilist'); // 추가 코드

						console.log("item.id: "+item.id); // 카드 고유 id
						console.log("item.listId: "+item.listId); // 카드 이전 listId
				
						var obj1 =$list.data('lobiList').$el;
						var obj2 = obj1[Object.keys(obj1)[0]];
		
						console.log("변경후 listId: "+obj2.id); // 
						// 1. 리스트가 변경 될 경우 해당 카드의 listId 바꾸기
						
						//	ajax 코드 추가 enableSortingAjax
							$.ajax({
								url:"/Task/cardIndexUpdateAjax",
								type:"get",
								data:{
									id : item.id,
									listId : obj2.id,
									cardIndex : currentIndex
								}
							})
						
						// 2. 리스트 내에서 순서 변경
							
						
						
						//    현재는 해당 리스트내의 id 순으로 정렬
						console.log("oldIndex: "+oldIndex); // 이전 순서
						console.log("currentIndex: "+currentIndex); //

						
						
						var $children = $itemWrapper.children().filter(function () {
							return this == $todo[0];
						});
						if ($children.length > 0) {
							if (me != oldList) {
								delete oldList.$items[item.id];
								me.$items[item.id] = item;
							}
							me._triggerEvent('afterItemReorder', [me, oldList, currentIndex, oldIndex, item, $todo]);
						}
					}
				});
			},

			_addItemToList: function (item) {
				var me = this;
				var $li = $('<li>', {
					'data-id': item.id,
					'class': 'lobilist-item'
				});
				$li.append($('<div>', {
					'class': 'lobilist-item-title',
					'html': item.title
				}));
//				console.log('item.id : '+item.id); //추가 코드	
//				console.log('item.title : '+item.title); //추가 코드

				if (item.description) {
					$li.append($('<div>', {
						'class': 'lobilist-item-description',
						html: item.description
					}));
				}
				if (item.dueDate) {
					$li.append($('<div>', {
						'class': 'lobilist-item-duedate',
						html: item.dueDate
					}));
				}
				$li = me._addItemControls($li);
				
				
				if (item.done) {
//					console.log("item.done이 true면 체크되어 추가되도록 하기"); //
//					console.log(item.done); //
					$li.find('input[type=checkbox]').prop('checked', true);
					$li.addClass('item-done');
				}
				$li.data('lobiListItem', item);
				me.$ul.append($li);
				me.$items[item.id] = item;

				if (me.isStateful()) {
					me.$lobiList.storageObject.addTodo(me.$lobiList.getId(), me.getId());
				}
				me._triggerEvent('afterItemAdd', [me, item]);
//				console.log('afterItemAdd 아이템 추가 후 뭘 할지 정하기?'); //추가 코드
				return $li;
			},

			_addItemControls: function ($li) {
				var me = this;
				if (me.$options.useCheckboxes) {
					$li.append(me._createCheckbox());
				}
				var $itemControlsDiv = $('<div>', {
					'class': 'todo-actions'
				}).appendTo($li);

				if (me.$options.enableTodoEdit) {
					$itemControlsDiv.append($('<div>', {
						'class': 'edit-todo todo-action',
						html: '<i class="glyphicon glyphicon-pencil"></i>'
					}).click(function () {
						me.editItem($(this).closest('li').data('id'));
					}));
				}

				if (me.$options.enableTodoRemove) {
					$itemControlsDiv.append($('<div>', {
						'class': 'delete-todo todo-action',
						html: '<i class="glyphicon glyphicon-remove"></i>'
					}).click(function () {


						//카드 삭제 부분 확인창
						if (confirm("정말 삭제하시겠습니까??") == true){    //확인

							me._onDeleteItemClick($(this).closest('li').data('lobiListItem'));

						}else{   

							return false;
						}

					}));
				}

				$li.append($('<div>', {
					'class': 'drag-handler'
				}));
				return $li;
			},

			_onDeleteItemClick: function (item) {
				this.deleteItem(item);
			},

			_updateItemInList: function (item) {
				var me = this;
				var $li = me.$lobiList.$el.find('li[data-id="' + item.id + '"]');
				$li.find('input[type=checkbox]').prop('checked', item.done);
				$li.find('.lobilist-item-title').html(item.title);
				$li.find('.lobilist-item-description').remove();
				$li.find('.lobilist-item-duedate').remove();

				if (item.description) {
					$li.append('<div class="lobilist-item-description">' + item.description + '</div>');
				}
				if (item.dueDate) {
					$li.append('<div class="lobilist-item-duedate">' + item.dueDate + '</div>');
				}
				$li.data('lobiListItem', item);
				$.extend(me.$items[item.id], item);
				me._triggerEvent('afterItemUpdate', [me, item]);
			},

			_triggerEvent: function (type, data) {
				var me = this;
				if (me.eventsSuppressed) {
					return;
				}
				if (me.$options[type] && typeof me.$options[type] === 'function') {
					return me.$options[type].apply(me, data);
				}
			},

			_removeItemFromList: function (item) {
				var me = this;
				me.$lobiList.$el.find('li[data-id=' + item.id + ']').remove();
				me._triggerEvent('afterItemDelete', [me, item]);
			},

			_sendAjax: function (url, params) {
				var me = this;
				return $.ajax(url, me._beforeAjaxSent(params))
			},

			_beforeAjaxSent: function (params) {
				var me = this;
				var eventParams = me._triggerEvent('beforeAjaxSent', [me, params]);
				return $.extend({}, params, eventParams || {});
			}
	};
//	||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||

	/**
	 * LobiList class
	 *
	 * @param {Object} $el - jQuery element
	 * @param {Object} options - Options for <code>LobiList</code> 'class'
	 * @constructor
	 */
	var LobiList = function ($el, options) {
		this.$el = $el;
		this.init(options);
	};

	LobiList.prototype = {
			$el: null,
			$lists: [],
			$options: {},

			eventsSuppressed: false,

			init: function (options) {
				var me = this;
				me.suppressEvents();
//				console.log('init에 들어가는 options : ' + JSON.stringify(options));
				me.$options = this._processInput(options);

				me.$el.addClass('lobilists');
				if (me.$options.onSingleLine) {
					me.$el.addClass('single-line');
				}

				me._createLists();
				me._handleSortable();
				me.resumeEvents();
				me._triggerEvent('init', [me]);
//				console.log('init 완료');

			},

			/**
			 * Get id for list
			 *
			 * @returns {int}
			 */
			getId: function () {
				var me = this;
				return me.$el.data('stateful-id');
			},

			/**
			 *
			 * @param options
			 * @returns {*}
			 * @private
			 */
			_processInput: function (options) {
				options = $.extend({}, $.fn.lobiList.DEFAULT_OPTIONS, options);
				if (options.actions.load) {

					console.log('_processInput load 기능 ajax 작동'); // 테스트용 코드

					$.ajax(options.actions.load, {
						async: false,   // 비동기인 'async: true'가 기본값
						data: {name: options.name}
					}).done(function (res) {
						options.lists = res.lists;
						// 비동기면 성공시 값을 받을 수 없다? 'async: false'사용 이유
					});
				}



				if (this.isStateful() && !options.storageObject) {
					this.storageObject = new StorageLocal();
//					console.log(this.storageObject);
				}
//				console.log(options);
				return options;
			},

			isStateful: function () {
//				console.log("isStateful LobiList.prototype"); // 추가 코드
				return !!this.getId();
			},

			/**
			 * @private
			 */
			_createLists: function () {
				var me = this;
				for (var i = 0; i < me.$options.lists.length; i++) {
					me.addList(me.$options.lists[i]);
				}
				return me;
			},

			/**
			 * @private
			 * @returns {LobiList}
			 */
			_handleSortable: function () {
				var me = this;
				
				console.log('_handleSortable');

				
				if (me.$options.sortable) {
					me.$el.sortable({
						items: '.lobilist-wrapper',
						handle: '.lobilist-header',
						cursor: 'move',
						placeholder: 'lobilist-placeholder',
						forcePlaceholderSize: true,
						opacity: 0.9,
						revert: 70,
						start: function (event, ui) {
							var $wrapper = ui.item;
							$wrapper.attr('data-previndex', $wrapper.index());
							
						},
						update: function (event, ui) {
							var $wrapper = ui.item,
							$list = $wrapper.find('.lobilist'),
							currentIndex = $wrapper.index(),
							oldIndex = parseInt($wrapper.attr('data-previndex'));
							me._triggerEvent('afterListReorder', [me, $list.data('lobiList'), currentIndex, oldIndex]);
							
							console.log('oldIndex : '+oldIndex);
							console.log('currentIndex : '+currentIndex);
							console.log('위치변경 리스트ID : '+$list.data()['dbId']);
			

							
							// 오른쪽으로 이동 oldIndex < currentIndex
							// oldIndex < * <=currentIndex
							// 2번 에서 4번으로 이동한다면
							// 기존의 3번 4번 자리의 인덱스 -1
							
							//for(var i =oldIndex; i<currentIndex;i++){
							//}
							
							// 왼쪽으로 이동 currentIndex < oldIndex
							// currentIndex <= * < oldIndex
							// 4번에서 2번으로 이동한다면
							// 기존의 2번 3번 자리의 인덱스 +1
						

							console.log('오른쪽에 있는 siblings : '+$wrapper.nextAll().length);
							for(var i =0; i<$wrapper.nextAll().length;i++){
								console.log($wrapper.nextAll()[i]);
								console.log($wrapper.nextAll()[i].lastChild.id);
								
							}
												
							console.log('왼쪽에 있는 siblings : '+($wrapper.prevAll().length-2)); // 왼쪽에 리스트생성버튼, 작업진행바 존재
							
							for(var i =0; i<$wrapper.prevAll().length -2 ;i++){
								console.log($wrapper.prevAll()[i].lastChild.id);
							}
							
							//다른 리스트들의 자리값과 id 구하기!

						}
					});
				} else {
					me.$el.addClass('no-sortable');
				}
				return me;
			},

			/**
			 * Add new list
			 *
			 * @public
			 * @method addList
			 * @param {List|Object} list - The <code>List</code> instance or <code>Object</code>
			 * @returns {List} Just added <code>List</code> instance
			 */
			addList: function (list) {
				console.log('addList');
				var me = this;
				if (!(list instanceof List)) {
					list = new List(me, me._processListOptions(list));
				}
				if (me._triggerEvent('beforeListAdd', [me, list]) !== false) {
					me.$lists.push(list);
					me.$el.append(list.$elWrapper);
					list.$el.data('lobiList', list);
					me._triggerEvent('afterListAdd', [me, list]);
				
				}

				return list;
			},

			/**
			 * Destroy the <code>LobiList</code>.
			 *
			 * @public
			 * @method destroy
			 * @returns {LobiList}
			 */
			destroy: function () {
				var me = this;
				if (me._triggerEvent('beforeDestroy', [me]) !== false) {
					for (var i = 0; i < me.$lists.length; i++) {
						me.$lists[i].remove();
					}
					if (me.$options.sortable) {
						me.$el.sortable("destroy");
					}
					me.$el.removeClass('lobilists');
					if (me.$options.onSingleLine) {
						me.$el.removeClass('single-line');
					}
					me.$el.removeData('lobiList');
					me._triggerEvent('afterDestroy', [me]);
				}

				return me;
			},

			/**
			 * Get next id which will be assigned to new item
			 *
			 * @public
			 * @method getNextId
			 * @returns {Number}
			 */
			getNextId: function () {
				var $items = this.$el.find('.lobilist-item');
				var maxId = 0;
				$items.each(function(index, item){
					var $item = $(item);
					if (parseInt($item.attr('data-id')) > maxId){
						maxId = parseInt($item.attr('data-id'));
					}
				});
				return maxId + 1;
			},

			/**
			 * Get next id which will be assigned to new item
			 *
			 * @public
			 * @method getNextListId
			 * @returns {Number}
			 */


			//lobilist-list- 가 아닌 숫자로 id 넣도록 변경
			getNextListId: function () {
//				console.log('getNextListId 동작')

				var $lists = this.$el.find('.lobilist');
				var maxId = 0;
//				var maxId = $('.lobilist').index(this);

				$lists.each(function(index, item){
					var $list = $(item).data('lobiList');
//					if ($list.getId().indexOf('lobilist-list-') === 0 &&
//					parseInt($list.getId().replace('lobilist-list-')) > maxId){
//					maxId = parseInt($list.getId().replace('lobilist-list-'));

					if ($list.getId() === 0 &&
							parseInt($list.getId()) > maxId){
						maxId = parseInt($list.getId());

					}
					maxId = $list.getId();
				});
//				return 'lobilist-list-' + (maxId + 1);
//				console.log('(maxId + 1) : '+ (parseInt(maxId) + parseInt(1)))
				return parseInt(maxId) + parseInt(1);
			},

			getListsPositions: function(){
				console.log('getListsPositions 동작')

				var positions = {};
				var $lists = this.$el.find('.lobilist-wrapper');
				$lists.each(function(ind, wrapper){
					var $list = $(wrapper).find('.lobilist');
					var list = $list.data('lobiList');
					positions[list.getId()] = $(wrapper).index() + 1;
				});
				return positions;
			},

			/**
			 *
			 * @param listOptions
			 * @returns {*}
			 * @private
			 */
			_processListOptions: function (listOptions) {
				var me = this;
				listOptions = $.extend({}, me.$options.listsOptions, listOptions);

				for (var i in me.$options) {
					if (me.$options.hasOwnProperty(i) && listOptions[i] === undefined) {
						listOptions[i] = me.$options[i];
					}
				}
				return listOptions;
			},

			suppressEvents: function () {
				this.eventsSuppressed = true;
				return this;
			},

			resumeEvents: function () {
				this.eventsSuppressed = false;
				return this;
			},

			/**
			 * @param type
			 * @param data
			 * @returns {*}
			 * @private
			 */
			_triggerEvent: function (type, data) {
				var me = this;
				if (me.eventsSuppressed) {
					return;
				}
				if (me.$options[type] && typeof me.$options[type] === 'function') {
					return me.$options[type].apply(me, data);
				} else {
					return me.$el.trigger(type, data);
				}
			}
	};

	$.fn.lobiList = function (option) {
		var args = arguments;
		var ret;
		return this.each(function () {
			var $this = $(this);
			var data = $this.data('lobiList');
			var options = typeof option === 'object' && option;

			if (!data) {
				$this.data('lobiList', (data = new LobiList($this, options)));
			}
			if (typeof option === 'string') {
				args = Array.prototype.slice.call(args, 1);
				ret = data[option].apply(data, args);
			}
		});
	};
	$.fn.lobiList.DEFAULT_OPTIONS = {
			// the name to identify board from others
			name: null,
			// Available style for lists
			'listStyles': ['lobilist-default', 'lobilist-danger', 'lobilist-success', 'lobilist-warning', 'lobilist-info', 'lobilist-primary'],
			// Default options for all lists
			listsOptions: {
				id: false,
				title: '',
				items: []
			},
			// Default options for all todo items
			itemOptions: {
				id: false,
				title: '',
				description: '',
				dueDate: '',
				done: false
			},

			lists: [],
			// Urls to communicate to backend for todos
			actions: {
				load: 'TaskAjax',
				insert: 'insert',
				delete: 'delete',
				update: 'update'
			},

			storage: null,

			storageObject: null,

			// Whether to show checkboxes or not
			useCheckboxes: true,
			// Show/hide todo remove button
			enableTodoRemove: true,
			// Show/hide todo edit button
			enableTodoEdit: true,
			// Whether to make lists and todos sortable
			sortable: true,
			// Default action buttons for all lists
			controls: ['edit', 'add', 'remove', 'styleChange'],
			//List style
			defaultStyle: 'lobilist-default',
			// Whether to show lists on single line or not
			onSingleLine: true,
			// This is used to generate angularJs directives instead of bootstrap plain html
			forAngularJs: false,

			// Events
			/**
			 * @event init
			 * Fires when <code>LobiList</code> is initialized
			 * @param {LobiList} The <code>LobiList</code> instance
			 */
			init: null,

			/**
			 * @event beforeDestroy
			 * Fires before <code>Lobilist</code> is destroyed. Return false if you do not want <code>LobiList</code> to be destroyed.
			 * @param {LobiList} The <code>LobiList</code> to be destroyed
			 */
			beforeDestroy: null,

			/**
			 * @event afterDestroy
			 * Fires after <code>Lobilist</code> is destroyed.
			 * @param {LobiList} The destroyed <code>LobiList</code> instance
			 */
			afterDestroy: null,

			/**
			 * @event beforeListAdd
			 * Fires before <code>List</code> is added to <code>LobiList</code>. Return false to prevent adding list.
			 * @param {LobiList} The <code>LobiList</code> instance
			 * @param {List} The <code>List</code> instance to be added
			 */
			beforeListAdd: null,

			/**
			 * @event afterListAdd
			 * Fires after <code>List</code> is added to <code>LobiList</code>.
			 * @param {LobiList} The <code>LobiList</code> instance
			 * @param {List} Just added <code>List</code> instance
			 */
			afterListAdd: null,

			/**
			 * @event beforeListRemove
			 * Fires before <code>List</code> is removed. Returning false will prevent removing the list
			 * @param {List} The <code>List</code> to be removed
			 */
			beforeListRemove: null,

			/**
			 * @event afterListRemove
			 * Fires after <code>List</code> is removed
			 * @param {List} The remove <code>List</code>
			 */
			afterListRemove: null,

			/**
			 * @event beforeItemAdd
			 * Fires before item is added in <code>List</code>. Return false if you want to prevent removing item
			 * @param {List} The <code>List</code> in which the item is going to be added
			 * @param {Object} The item object
			 */
			beforeItemAdd: null,

			/**
			 * @event afterItemAdd
			 * Fires after item is added in <code>List</code>
			 * @param {List} The <code>List</code> in which the item is added
			 * @param {Object} The item object
			 */
			afterItemAdd: null,

			/**
			 * @event beforeItemUpdate
			 * Fires before item is updated. Returning false will prevent updating item
			 * @param {List} The <code>List</code> instance
			 * @param {Object} The item object which is going to be updated
			 */
			beforeItemUpdate: null,

			/**
			 * @event afterItemUpdate
			 * Fires after item is updated
			 * @param {List} The <code>List</code> object
			 * @param {Object} The updated item object
			 */
			afterItemUpdate: null,

			/**
			 * @event beforeItemDelete
			 * Fires before item is deleted. Returning false will prevent deleting the item
			 * @param {List} The <code>List</code> instance
			 * @param {Object} The item object to be deleted
			 */
			beforeItemDelete: null,

			/**
			 * @event afterItemDelete
			 * Fires after item is deleted.
			 * @param {List} The <code>List</code> object
			 * @param {Object} The deleted item object
			 */
			afterItemDelete: null,

			/**
			 * @event afterListReorder
			 * Fires after <code>List</code> position is changed among its siblings
			 * @param {LobiList} The <code>LobiList</code> instance
			 * @param {List} The <code>List</code> instance which changed its position
			 */
			afterListReorder: null,

			/**
			 * @event afterItemReorder
			 * Fires after item position is changed (it is reordered) in list
			 * @param {List} The <code>List</code> instance
			 * @param {Object} The jQuery object of item
			 */
			afterItemReorder: null,

			/**
			 * @event afterMarkAsDone
			 * Fires after item is marked as done.
			 * @param {List} The <code>List</code> instance
			 * @param {Object} The jQuery checkbox object
			 */
			afterMarkAsDone: null,

			/**
			 * @event afterMarkAsUndone
			 * Fires after item is marked as undone
			 * @param {List} The <code>List</code> instance
			 * @param {Object} The jQuery checkbox object
			 */
			afterMarkAsUndone: null,

			/**
			 * @event beforeAjaxSent
			 * Fires before ajax call is sent to backend. This event is very useful is you want to add default parameters
			 * or headers to every request. Such as CSRF token parameter or Access Token header
			 * @param {List} The <code>List</code> instance
			 * @param {Object} The jquery ajax parameters object. You can add additional headers or parameters
			 * to this object and must return the object which will be used for sending request
			 */
			beforeAjaxSent: null,

			/**
			 * @event styleChange
			 * Fires when list style is changed
			 * @param {List} The <code>List</code> instance
			 * @param {String} Old class name
			 * @param {String} New class name
			 */
			styleChange: null,

			/**
			 * @event titleChange
			 * Fires when list title is change
			 * @param {List} The <code>List</code> instance
			 * @param {String} Old title name
			 * @param {String} New title name
			 */
			titleChange: null
	};
});