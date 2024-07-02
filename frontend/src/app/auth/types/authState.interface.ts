import { Errors } from '../../shared/types/errors.interface';
import { UserInterface } from '../../shared/types/user.interface';

export interface AuthStateInterface {
  isSubmitting: boolean;
  currentUser: UserInterface | null | undefined;
  errors: Errors | null;
  isLoading: boolean;
}
