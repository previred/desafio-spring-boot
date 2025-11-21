import { useState, useEffect } from 'react';
import Login from './components/Login';
import Register from './components/Register';
import TaskDashboard from './components/TaskDashboard';
import { authService } from './services/api';
import './App.css';

function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [showRegister, setShowRegister] = useState(false);

  useEffect(() => {
    setIsAuthenticated(authService.isAuthenticated());
  }, []);

  const handleLoginSuccess = () => {
    setIsAuthenticated(true);
  };

  const handleRegisterSuccess = () => {
    setShowRegister(false);
    alert('Registro exitoso. Por favor inicia sesión.');
  };

  const handleLogout = () => {
    authService.logout();
    setIsAuthenticated(false);
  };

  if (isAuthenticated) {
    return <TaskDashboard onLogout={handleLogout} />;
  }

  if (showRegister) {
    return (
      <Register
        onRegisterSuccess={handleRegisterSuccess}
        onToggleLogin={() => setShowRegister(false)}
      />
    );
  }

  return (
    <Login
      onLoginSuccess={handleLoginSuccess}
      onToggleRegister={() => setShowRegister(true)}
    />
  );
}

export default App;
