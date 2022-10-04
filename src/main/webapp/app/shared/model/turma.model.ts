import dayjs from 'dayjs';
import { IPessoa } from 'app/shared/model/pessoa.model';
import { SexoTurma } from 'app/shared/model/enumerations/sexo-turma.model';
import { FaixaEtaria } from 'app/shared/model/enumerations/faixa-etaria.model';

export interface ITurma {
  id?: string;
  nome?: string;
  sexoTurma?: SexoTurma;
  faixaEtaria?: FaixaEtaria;
  dataInicio?: string;
  dataFim?: string | null;
  dataCadastro?: string | null;
  professor?: IPessoa | null;
  alunos?: IPessoa[] | null;
}

export const defaultValue: Readonly<ITurma> = {};
