import { Status } from '../../../types/status.interface';

export interface InboxStateInterface {
  statuses: { [key: number]: Status };
  count: number;
}
