import { Email } from '../../shared/types/email.interface';
import { Status } from '../../shared/types/status.interface';

export interface EmailDetailStateInterface {
  status: Status | null | undefined;
  emails: Email[];
}
