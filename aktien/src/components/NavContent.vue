<template>
  <div class="navContent">
    <b-navbar toggleable="lg" type="dark" variant="info">
      <b-navbar-brand to=/overview>Aktienverwaltung</b-navbar-brand>
      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
          <b-nav-item to="/add">Add</b-nav-item>
          <b-nav-item to="/manage">Manage</b-nav-item>
          <b-nav-item to="/profil">Profil</b-nav-item>
        </b-navbar-nav>
      </b-collapse>
      <b-navbar-nav class="ml-auto">
        <b-dropdown id="dropdown-form" text="Login" ref="dropdown" class="m-2">
      <b-dropdown-form>
        <b-form-group label="Email" label-for="dropdown-form-email" @submit.stop.prevent>
          <b-form-input
            id="dropdown-form-email"
            size="sm"
            placeholder="email@example.com"
          ></b-form-input>
        </b-form-group>

        <b-form-group label="Password" label-for="dropdown-form-password">
          <b-form-input
            id="dropdown-form-password"
            type="password"
            size="sm"
            placeholder="Password"
          ></b-form-input>
        </b-form-group>

        <b-form-checkbox class="mb-3">Remember me</b-form-checkbox>
        <b-button variant="primary" size="sm" @click="onClick">Sign In</b-button>
      </b-dropdown-form>
      <b-dropdown-divider></b-dropdown-divider>
      <b-dropdown-item-button>New around here? Sign up</b-dropdown-item-button>
      <b-dropdown-item-button>Forgot Password?</b-dropdown-item-button>
    </b-dropdown>
    </b-navbar-nav>
    </b-navbar>
    
  </div>
</template>

<script>

export default {
  name: 'NavContent',
  components: {
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
div.navContent {

  margin: 10px;
}
a {
  padding: 10px;
}
</style>
