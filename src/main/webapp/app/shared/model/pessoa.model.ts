import dayjs from 'dayjs';
import { ITelefone } from 'app/shared/model/telefone.model';
import { IEndereco } from 'app/shared/model/endereco.model';
import { ITurma } from 'app/shared/model/turma.model';
import { IResponsavel } from 'app/shared/model/responsavel.model';
import { Sexo } from 'app/shared/model/enumerations/sexo.model';
import { TipoPessoa } from 'app/shared/model/enumerations/tipo-pessoa.model';

export interface IPessoa {
  id?: string;
  nome?: string;
  dataNascimento?: string;
  cpf?: string;
  sexo?: Sexo;
  tipoPessoa?: TipoPessoa;
  dataCadastro?: string | null;
  dataAtualizacao?: string | null;
  telefones?: ITelefone[] | null;
  enderecos?: IEndereco[] | null;
  turma?: ITurma | null;
  responsaveis?: IResponsavel[] | null;
}

export const defaultValue: Readonly<IPessoa> = {};
