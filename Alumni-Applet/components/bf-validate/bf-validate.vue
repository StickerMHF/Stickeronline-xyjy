<template>
	<text></text>
</template>

<script>
import _ from 'lodash'
import schema from 'async-validator'
import {messages} from './messages'
export default {
	props: {
		model: {
			type: Object,
			default: () => {}
		},
		rules: {
			type: Object,
			default: () => {}
		}
	},
	methods: {
		validate() {
			let me = this
			return new Promise((resolve, reject) => { 
				let validator = new schema(me.rules)
				validator.messages(messages);
				validator.validate(me.model, (errors, fields) => {
					if (errors) {
						me.handleErrors(errors, fields)
						reject(errors)
					} else {
						resolve()
					}
				})
			})
		},
		handleErrors(errors, fields) {
			let error = _.first(errors)
			let originalName = this.rules[error.field].name
			uni.showToast({
				icon: 'none',
				title: _.replace(error.message, error.field, originalName)
			})
		}
	}
}
</script>

<style></style>
