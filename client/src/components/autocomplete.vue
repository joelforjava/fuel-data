<!--
  This component courtesy of https://alligator.io/vuejs/vue-autocomplete-component/

  TODO - update to where the list goes away once no items remain (e.g. you're entering a new value)
  TODO - update to where it will go away when the input loses focus AT ALL
       - As of now, you have to tab out or click well below the input for it to properly go away
         if you don't select a value, hit enter, or hit tab
  TODO - also, if you clear a form that contains this, any '<autocomplete>' items will retain their value
-->
<template>
    <div class="autocomplete">
      <input type="text"
             class="form-control"
             v-model="search"
             @input="onChange"
             @keydown.down="onArrowDown"
             @keydown.up="onArrowUp"
             @keydown.enter="onEnter"
             @keydown.tab="onEnter"
             data-lpignore="true" />
      <ul v-show="isOpen" class="autocomplete-results">
        <li v-if="isLoading" class="loading">
          Loading Results...
        </li>
        <li v-else v-for="(result, i) in results"
            :key="i"
            @click="setResult(result)"
            class="autocomplete-result"
            :class="{ 'is-active': i === arrowCounter }">
          {{result}}
        </li>
      </ul>
    </div>
</template>

<script>
export default {
  name: 'autocomplete',
  props: {
    items: {
      type: Array,
      required: false,
      default: () => []
    },
    isAsync: {
      type: Boolean,
      required: false,
      default: false
    }
  },
  data () {
    return {
      search: '',
      results: [],
      isOpen: false,
      isLoading: false,
      arrowCounter: -1
    }
  },
  methods: {
    onChange () {
      this.$emit('input', this.search)

      if (this.isAsync) {
        this.isLoading = true
      } else {
        this.filterResults()
        this.isOpen = true
      }
    },
    filterResults () {
      this.results = this.items.filter(item => item.toLowerCase().indexOf(this.search.toLowerCase()) > -1)
    },
    setResult (result) {
      this.search = result
      this.isOpen = false
    },
    onArrowDown () {
      if (this.arrowCounter < this.results.length) {
        this.arrowCounter++
      }
    },
    onArrowUp () {
      if (this.arrowCounter > 0) {
        this.arrowCounter--
      }
    },
    onEnter () {
      if (this.results[this.arrowCounter]) {
        this.search = this.results[this.arrowCounter]
      }
      this.isOpen = false
      this.arrowCounter = -1
    },
    handleClickOutside (evt) {
      if (!this.$el.contains(evt.target)) {
        this.isOpen = false
        this.arrowCounter = -1
      }
    }
  },
  watch: {
    items: function (value, oldValue) {
      if (this.isAsync) {
        this.results = value
        this.isOpen = true
        this.isLoading = false
      }
    }
  },
  mounted () {
    document.addEventListener('click', this.handleClickOutside)
  },
  destroyed () {
    document.removeEventListener('click', this.handleClickOutside)
  }
}
</script>

<style scoped>
  .autocomplete {
    position: relative;
    display: inline-block;
    width: 100%;
  }

  .autocomplete-results {
    position: absolute;
    /*border: 1px solid #d4d4d4;*/
    border: 1px solid #EEEEEE;
    border-bottom: none;
    border-top: none;
    padding: 0;
    margin: 0;
    height: 120px;
    overflow: auto;
    z-index: 99;
    top: 100%;
    left: 0;
    right: 0;
  }
  .autocomplete-result {
    list-style: none;
    text-align: left;
    padding: 4px 2px;
    background-color: white;
    cursor: pointer;
  }

  .autocomplete-result.is-active,
  .autocomplete-result:hover {
    background-color: #4aae9b;
    color: white;
  }
</style>
