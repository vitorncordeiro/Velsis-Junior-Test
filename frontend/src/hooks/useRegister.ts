import { useState } from 'react';
import { api } from '../services/api.service';

export function useRegister() {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const register = async (username: string, password: string, email: string) => {
    setLoading(true);
    setError(null);
    try {
      const result = await api.register({ username, password, email });
      return result;
    } catch (err) {
      const msg = err instanceof Error ? err.message : 'Registration failed';
      setError(msg);
      throw err;
    } finally {
      setLoading(false);
    }
  };

  return { register, loading, error };
}
