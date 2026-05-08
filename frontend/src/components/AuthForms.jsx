function AuthForms({
  authMode,
  setAuthMode,
  registerForm,
  loginForm,
  handleRegisterChange,
  handleLoginChange,
  handleRegister,
  handleLogin,
}) {
  const showLogin = authMode === "login";

  return (
    <div className="auth-page">
      {showLogin ? (
        <form className="auth-card" onSubmit={handleLogin}>
          <h2>Login</h2>

          <input
            name="email"
            type="email"
            placeholder="Email"
            value={loginForm.email}
            onChange={handleLoginChange}
          />

          <input
            name="password"
            type="password"
            placeholder="Password"
            value={loginForm.password}
            onChange={handleLoginChange}
          />

          <button type="submit">Login</button>

          <p className="auth-switch">
            Nu ai cont?{" "}
            <button
              type="button"
              className="link-button"
              onClick={() => setAuthMode("register")}
            >
              Creează cont
            </button>
          </p>
        </form>
      ) : (
        <form className="auth-card" onSubmit={handleRegister}>
          <h2>Register</h2>

          <input
            name="username"
            placeholder="Username"
            value={registerForm.username}
            onChange={handleRegisterChange}
          />

          <input
            name="email"
            type="email"
            placeholder="Email"
            value={registerForm.email}
            onChange={handleRegisterChange}
          />

          <input
            name="password"
            type="password"
            placeholder="Password"
            value={registerForm.password}
            onChange={handleRegisterChange}
          />

          <button type="submit">Register</button>

          <p className="auth-switch">
            Ai deja cont?{" "}
            <button
              type="button"
              className="link-button"
              onClick={() => setAuthMode("login")}
            >
              Înapoi la login
            </button>
          </p>
        </form>
      )}
    </div>
  );
}

export default AuthForms;