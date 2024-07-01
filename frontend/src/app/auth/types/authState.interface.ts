import { ErrorMap } from '../../shared/types/errorMap.type';
import { UserInterface } from '../../shared/types/user.interface';

export interface AuthStateInterface {
  isSubmitting: boolean;
  currentUser: UserInterface | null | undefined;
  errors: ErrorMap | null;
  isLoading: boolean;
}
