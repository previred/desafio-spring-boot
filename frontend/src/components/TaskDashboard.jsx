import { useState, useEffect } from 'react';
import { authService, taskService, taskStateService } from '../services/api';
import TaskList from './TaskList';
import TaskForm from './TaskForm';

export default function TaskDashboard({ onLogout }) {
  const [tasks, setTasks] = useState([]);
  const [taskStates, setTaskStates] = useState([]);
  const [selectedTask, setSelectedTask] = useState(null);
  const [showForm, setShowForm] = useState(false);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [filter, setFilter] = useState({ stateId: '' });

  const currentUser = authService.getCurrentUser();

  useEffect(() => {
    loadTaskStates();
    loadTasks();
  }, [filter]);

  const loadTaskStates = async () => {
    try {
      const states = await taskStateService.getTaskStates();
      setTaskStates(states);
    } catch (err) {
      setError('Error al cargar estados de tarea');
    }
  };

  const loadTasks = async () => {
    try {
      setLoading(true);
      const filterParams = {};
      if (filter.stateId) filterParams.stateId = filter.stateId;

      const data = await taskService.getTasks(filterParams);
      setTasks(data);
      setError('');
    } catch (err) {
      setError('Error al cargar tareas');
    } finally {
      setLoading(false);
    }
  };

  const handleCreateTask = () => {
    setSelectedTask(null);
    setShowForm(true);
  };

  const handleEditTask = (task) => {
    setSelectedTask(task);
    setShowForm(true);
  };

  const handleDeleteTask = async (taskId) => {
    if (!confirm('¿Estás seguro de eliminar esta tarea?')) return;

    try {
      await taskService.deleteTask(taskId);
      loadTasks();
    } catch (err) {
      setError('Error al eliminar tarea');
    }
  };

  const handleSaveTask = async (taskData) => {
    try {
      if (selectedTask) {
        await taskService.updateTask(selectedTask.id, taskData);
      } else {
        await taskService.createTask(taskData);
      }
      setShowForm(false);
      setSelectedTask(null);
      loadTasks();
    } catch (err) {
      throw new Error('Error al guardar tarea');
    }
  };

  const handleCancelForm = () => {
    setShowForm(false);
    setSelectedTask(null);
  };

  return (
    <div className="dashboard">
      <header className="dashboard-header">
        <div>
          <h1>Gestión de Tareas</h1>
          <p>Bienvenido, {currentUser?.fullName || currentUser?.username}</p>
        </div>
        <button onClick={onLogout} className="btn-secondary">
          Cerrar Sesión
        </button>
      </header>

      {error && <div className="error-message">{error}</div>}

      <div className="dashboard-actions">
        <button onClick={handleCreateTask} className="btn-primary">
          + Nueva Tarea
        </button>

        <div className="filter-group">
          <label htmlFor="stateFilter">Filtrar por estado:</label>
          <select
            id="stateFilter"
            value={filter.stateId}
            onChange={(e) => setFilter({ ...filter, stateId: e.target.value })}
          >
            <option value="">Todos los estados</option>
            {taskStates.map((state) => (
              <option key={state.id} value={state.id}>
                {state.name}
              </option>
            ))}
          </select>
        </div>
      </div>

      {showForm && (
        <TaskForm
          task={selectedTask}
          taskStates={taskStates}
          onSave={handleSaveTask}
          onCancel={handleCancelForm}
        />
      )}

      <TaskList
        tasks={tasks}
        taskStates={taskStates}
        loading={loading}
        onEdit={handleEditTask}
        onDelete={handleDeleteTask}
      />
    </div>
  );
}
