<template>
  <div class="form-group"
       :class="{
          'input-group': hasIcon,
          'input-group-focus': focused
       }">
    <slot name="label">
      <label v-if="label" class="control-label">
        {{label}}
      </label>
    </slot>
    <slot name="addonLeft">
      <span v-if="addonLeftIcon" class="input-group-prepend">
        <div class="input-group-text">
          <i :class="addonLeftIcon"></i>
        </div>
      </span>
    </slot>
    <slot>
      <span><i v-if="addArrowIcon" class="fas fa-angle-down" style="padding-left: 5px;color:red"></i></span>
      <input
        :value="value"
        v-bind="$attrs"
        v-on="listeners"
        class="form-control"
        aria-describedby="addon-right addon-left">
    </slot>
    <slot name="addonRight">
      <span v-if="addonRightIcon" class="input-group-append">
        <div class="input-group-text">
          <i :class="addonRightIcon"></i>
        </div>
      </span>
    </slot>
    <slot name="helperText"></slot>
  </div>
</template>
<script>
  export default {
    inheritAttrs: false,
    name: "base-input",
    props: {
      colorArrow :  {
        type: String,
        description: "Color for arrow "
      },
      label: {
        type: String,
        description: "Input label"
      },
      value: {
        type: [String, Number],
        description: "Input value"
      },
      addonRightIcon: {
        type: String,
        description: "Input icon on the right"
      },
      addonLeftIcon: {
        type: String,
        description: "Input icon on the left"
      },
      addArrowIcon: {
        type: String,
        description: "Input icon on the left"
      },
    },
    model: {
      prop: 'value',
      event: 'input'
    },
    data() {
      return {
        focused: false
      }
    },
    computed: {
      hasIcon() {
        const { addonRight, addonLeft } = this.$slots;
        return addonRight !== undefined || addonLeft !== undefined || this.addonRightIcon !== undefined || this.addonLeftIcon !== undefined;
      },
      listeners() {
        return {
          ...this.$listeners,
          input: this.onInput,
          blur: this.onBlur,
          focus: this.onFocus
        }
      }
    },
    methods: {
      onInput(evt) {
        this.$emit('input', evt.target.value)
      },
      onFocus() {
        this.focused = true;
      },
      onBlur() {
        this.focused = false;
      }
    }
  }
</script>
<style>

</style>
